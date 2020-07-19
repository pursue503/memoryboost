package com.memoryboost.service.member;

import com.memoryboost.domain.dto.member.memoryboost.request.MemberUpdateRequestDTO;
import com.memoryboost.domain.dto.member.memoryboost.response.MemberFindByLoginIdResponseDTO;
import com.memoryboost.domain.dto.member.sns.MemberSNSInfoUpdateRequestDTO;
import com.memoryboost.domain.vo.member.MemberCustomVO;
import com.memoryboost.util.email.MemoryBoostMailTemplate;
import com.memoryboost.domain.dto.member.memoryboost.request.MemberSaveRequestDTO;
import com.memoryboost.domain.dto.member.sns.OAuthAttributesDTO;
import com.memoryboost.domain.entity.email.MemberEmail;
import com.memoryboost.domain.entity.email.MemberEmailRepository;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.vo.member.MemberOAuth2VO;
import com.memoryboost.domain.vo.member.MemberVO;
import com.memoryboost.util.email.MemoryBoostMailhandler;
import com.memoryboost.util.email.MemoryBoostPwAuthCodeDelete;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    //이메일 전송 객체
    @Autowired
    private JavaMailSender javaMailSender;

    //회원
    @Autowired
    private MemberRepository memberRepository;

    //이메일
    @Autowired
    private MemberEmailRepository memberEmailRepository;

    //비밀번호 암호화
    @Autowired
    private PasswordEncoder passwordEncoder;

    //이메일 전송 템플릿 ( 코드생성 메일양식생성 )
    @Autowired
    private MemoryBoostMailTemplate mailTemplate;

    //memberEmail
    @Autowired
    private MemberEmailRepository emailRepository;

    //자사서비스 이름 sns 구분명
    private final String memoryboost = "memoryboost";

    //회원로그인
    @Override // 로그인관리.
    public UserDetails loadUserByUsername(String memberLoginId) throws UsernameNotFoundException {

        Member member = memberRepository.findByMemberLoginIdAndMemberSns(memberLoginId,memoryboost);

        if(member == null) throw new UsernameNotFoundException(memberLoginId);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRoleKey()));

        return new MemberVO(member,authorities);
//        return new MemberCustomVO(member,authorities);
    }

    //SNS 외부 로그인 처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();


        OAuthAttributesDTO attributes = OAuthAttributesDTO.
                of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes,registrationId);

//        return new MemberOAuth2VO(member,
//                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
//                attributes.getAttributes(),
//                attributes.getNameAttributeKey());
        return new MemberCustomVO(member,
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());

    }

    private Member saveOrUpdate(OAuthAttributesDTO attributes , String registrationId) {

        //registrationId = sns 구분

        Member member = memberRepository.findByMemberEmailAndMemberSns(attributes.getMemberEmail(),registrationId)
                .map(entity -> entity.snsUpdate(attributes.getMemberEmail(),attributes.getMemberName()))//정보 업데이트
                .orElse(attributes.toEntity()); // 존재하지않으면 DTO 정보로 build

        return memberRepository.save(member);
    }

    //회원서비스


    //아이디 중복체크
    @Transactional(readOnly = true)
    public boolean loginIdOverlapCheck(String memberLoginId) {
        return memberRepository.countByMemberLoginId(memberLoginId) >= 1 ? true : false;
    }

    //회원가입 트랜잭션 실행 , 한번에여기서 메일보내기까지 실행
    @Transactional
    public boolean signUp(MemberSaveRequestDTO saveRequestDto)  {

        if(!saveRequestDto.patternCheck()){  // 정규식에 일치하면 true , ! 반전해서 일치하면 false if문 패스
            return false;
        }

        saveRequestDto.setMemberPw(passwordEncoder.encode(saveRequestDto.getMemberPw()));


        Member member = memberRepository.save(saveRequestDto.toEntity());

        MemberEmail memberEmail = memberEmailRepository.save(new MemberEmail().builder()
        .memberId(member)
        .emailCode(mailTemplate.createRandomCode(12)).build());

        try {
            MemoryBoostMailhandler mailhandler = new MemoryBoostMailhandler(javaMailSender);
            mailhandler.setTo(member.getMemberEmail()); // 받는사람 회원이메일
            mailhandler.setSubject("MemoryBoost 회원가입 인증!");
            mailhandler.setText(mailTemplate.signUpAuthMailTemplate(memberEmail));
            mailhandler.send();
        } catch (MessagingException e) {
            return false;
        }

        return true;
    }

    //이메일인증확인 , + 상태 업데이트 
    @Transactional // 사용
    public boolean memberEmailAuthCheck(Long memberid, Long emailNo, String emailCode) {

        Optional<Object> member = memberRepository.findById(memberid).map(entity ->
                emailRepository.countByEmailNoAndMemberIdAndEmailCode(emailNo, entity, emailCode) == 1 ?
                entity.emailAuthCompleteAndMemberStUpdate() : entity);
        try{
            Member memberEntity = (Member) member.get();
            if(memberEntity.isMemberSt()) { // 위에서 email 코드가 일치하면 true가됨
                MemberEmail memberEmail = memberEmailRepository.findById(emailNo).get();
                memberEmailRepository.delete(memberEmail);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }
    
    //SNS 접속자 정보 업데이트
    @Transactional
    public Boolean snsMemberInfoUpate(Authentication authentication,MemberSNSInfoUpdateRequestDTO updateRequestDTO){
        //업데이트

        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();

        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다." + memberCustomVO.getMemberId()));

        member.snsMemberInfoUpdate(updateRequestDTO);

        return true;
    }
    
    //회원아이디찾기
    @Transactional(readOnly = true) //읽기전용이라 사용을안하겠음.
    public List<MemberFindByLoginIdResponseDTO> memberFindByLoginId(String memberEmail) {
        //email 과 sns 구분으로 아이디를찾음
        List<MemberFindByLoginIdResponseDTO> findByLoginIdResponseDTOList = new ArrayList<>();
        List<Member> memberList = memberRepository.findMemberLoginId(memberEmail,memoryboost);

        for (Member member : memberList) {
            findByLoginIdResponseDTOList.add(new MemberFindByLoginIdResponseDTO(member));
        }
        return findByLoginIdResponseDTOList;
    }

    //비밀번호 찾기 아이디 존재유무
    @Transactional(readOnly = true) // 읽기전용 SELECT
    public boolean memberExistenceCheck(String memberLoginId,String memberEmail) {
        return memberRepository.countByMemberLoginIdAndMemberEmail(memberLoginId,memberEmail) == 1 ? true : false;
    }

    //인증코드 생성 + 전송
    @Transactional
    public boolean memberFindByPwAuthCodeSend(String memberLoginId, String memberEmail) {

        MemberEmail emailEntity =  memberEmailRepository.save(new MemberEmail().builder()
        .memberId(memberRepository.findByMemberLoginIdAndMemberSns(memberLoginId,memoryboost)).emailCode(mailTemplate.createRandomCode(6)).build());

        try{
            MemoryBoostMailhandler mailhandler = new MemoryBoostMailhandler(javaMailSender);
            mailhandler.setTo(memberEmail); // 받는사람 회원이메일
            mailhandler.setSubject("Memoryboost 비밀번호 찾기 인증번호");
            mailhandler.setText(mailTemplate.findByPwAuthCodeTemplate(emailEntity));
            mailhandler.send();

            //메일전송후 3분뒤 메일삭제
            Thread thread = new Thread(new MemoryBoostPwAuthCodeDelete(memberEmailRepository,emailEntity));
            thread.start();
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean findByMemberPw(String memberLoginId, String emailCode){

        Member member = memberRepository.findByMemberLoginIdAndMemberSns(memberLoginId,memoryboost);

        boolean flag = memberEmailRepository.countByMemberIdAndEmailCode(member,emailCode) == 1 ? true : false;

        if(flag) {
            String changePw = mailTemplate.createRandomCode(12);
            member.memberPwChange(passwordEncoder.encode(changePw));

            try{
                MemoryBoostMailhandler mailhandler = new MemoryBoostMailhandler(javaMailSender);
                mailhandler.setTo(member.getMemberEmail()); // 받는사람 회원이메일
                mailhandler.setSubject("Memoryboost 비밀번호 찾기");
                mailhandler.setText(mailTemplate.changePwTemplate(changePw));
                mailhandler.send();
                return true;
            } catch (MessagingException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    //마이페이지 회원정보수정 비밀번호확인
    public boolean mypagePasswordConfirm(Long memberId, String memberPw ) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NullPointerException("아이디가 존재 하지 않습니다."));
        return passwordEncoder.matches(memberPw,member.getMemberPw());
    }
    //회원정보 업데이트
    @Transactional
    public boolean memberUpdate(Authentication authentication, MemberUpdateRequestDTO updateRequestDTO) {

        Pattern pw = Pattern.compile("^(?=.*?[^\\s])[\\w\\d]{6,12}$");
        Matcher pwMatcher = pw.matcher(updateRequestDTO.getMemberPw());
        if (!pwMatcher.find()) {
            return false;
        }

        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();

        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(() -> new NullPointerException("아이디가 존재 하지 않습니다."));

        member.memberUpdate(updateRequestDTO,passwordEncoder);

        return true;

    }

}
