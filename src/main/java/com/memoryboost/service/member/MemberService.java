package com.memoryboost.service.member;

import com.memoryboost.util.email.MemoryBoostMailTemplate;
import com.memoryboost.domain.dto.member.MemberSaveRequestDTO;
import com.memoryboost.domain.dto.member.OAuthAttributesDTO;
import com.memoryboost.domain.entity.email.MemberEmail;
import com.memoryboost.domain.entity.email.MemberEmailRepository;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.vo.member.MemberOAuth2VO;
import com.memoryboost.domain.vo.member.MemberVO;
import com.memoryboost.util.email.MemoryBoostMailhandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    //회원로그인
    @Override // 로그인관리.
    public UserDetails loadUserByUsername(String memberLoginId) throws UsernameNotFoundException {

        Member member = memberRepository.findByMemberLoginIdAndMemberSns(memberLoginId,"memoryboost");

        if(member == null) throw new UsernameNotFoundException(memberLoginId);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRoleKey()));

        return new MemberVO(member,authorities);
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

        return new MemberOAuth2VO(member,
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

    //회원가입

    //아이디 중복체크
    @Transactional
    public boolean loginIdOverlapCheck(String memberLoginId) {
        return memberRepository.countByMemberLoginId(memberLoginId) >= 1 ? true : false;
    }

    //회원가입 트랜잭션 실행 , 한번에여기서 메일보내기까지 실행
    @Transactional
    public boolean signUp(MemberSaveRequestDTO saveRequestDto)  {


        saveRequestDto.setMemberPw(passwordEncoder.encode(saveRequestDto.getMemberPw()));

        Member member = memberRepository.save(saveRequestDto.toEntity());

        MemberEmail memberEmail = memberEmailRepository.save(new MemberEmail().builder()
        .memberId(member)
        .emailCode(mailTemplate.createRandomCode()).build());

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

}
