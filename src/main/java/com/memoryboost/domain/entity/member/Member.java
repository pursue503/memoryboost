package com.memoryboost.domain.entity.member;

import com.memoryboost.domain.dto.member.memoryboost.request.MemberUpdateRequestDTO;
import com.memoryboost.domain.dto.member.sns.MemberSNSInfoUpdateRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true,nullable = false)
    private String memberLoginId;

    @Column(nullable = false)
    private String memberPw;

    @Column
    private String memberName;

    @Column
    private String memberTel;

    @Column
    private String memberZipCode;

    @Column
    private String memberAddress;

    @Column
    private String memberDetailAddress;

    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date memberSignupDate;

    @Column // default 0
    @ColumnDefault("0")
    private boolean memberSt;

    //로그인유형 그냥 sns로..  , 자체서비스 회원가입 memoryboost , 나머지 외부서비스 이름.
    @Column(nullable = false)
    private String memberSns;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role memberAuth;

    //회원이메일
    private String memberEmail;

    @Builder
    public Member(String memberLoginId, String memberPw, String memberName, String memberTel, String memberZipCode,
                  String memberAddress, String memberDetailAddress, String memberSns,
                  Role memberAuth, String memberEmail) {
        this.memberLoginId = memberLoginId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.memberZipCode = memberZipCode;
        this.memberAddress = memberAddress;
        this.memberDetailAddress = memberDetailAddress;
        this.memberSns = memberSns;
        this.memberAuth = memberAuth;
        this.memberEmail = memberEmail;
    }



    public String getRoleKey() {
        return this.memberAuth.getKey();
    }

    //외부로그인중 이미 존재하는 계정이면 새로운 정보로 업데이트 시켜줌,
    //외부로그인은 id pw 가 없다.
    public Member snsUpdate(String memberEmail, String memberName) {
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        return this; // 리턴해서 현재정보를 사용하기 위함.
    }

    //이메일인증완료시 아이디 사용가능
    public Member emailAuthCompleteAndMemberStUpdate(){
        this.memberSt = true;
        return this; // 리턴해서 현재정보를 사용하기 위함.
    }

    //SNS 회원 정보 업데이트 entity 는 메소드 종료시 영속성에서 변경된 데이터를 업데이트함
    //그래서 return this 를 할필요가 없음 사용을 안할거기때문. 위에 두메소드는 사용을위해여 return this
    public void snsMemberInfoUpdate(MemberSNSInfoUpdateRequestDTO updateRequestDTO){
        this.memberTel = updateRequestDTO.getMemberTel();
        this.memberZipCode = updateRequestDTO.getMemberZipCode();
        this.memberAddress = updateRequestDTO.getMemberAddress();
        this.memberDetailAddress = updateRequestDTO.getMemberDetailAddress();
    }

    //회원비밀번호찾기 비밀번호 업데이트
    public void memberPwChange(String changePw){
        this.memberPw = changePw;
    }

    //회원 정보 업데이트
    public void memberUpdate(MemberUpdateRequestDTO updateRequestDTO, PasswordEncoder passwordEncoder) {

        if(updateRequestDTO.getMemberPw() != null && !updateRequestDTO.getMemberPw().equals("")) {
            this.memberPw = passwordEncoder.encode(updateRequestDTO.getMemberPw());
        }
        if(updateRequestDTO.getMemberName() != null && !updateRequestDTO.getMemberName().equals("")) {
            this.memberName = updateRequestDTO.getMemberName();
        }
        if(updateRequestDTO.getMemberTel() != null && !updateRequestDTO.getMemberTel().equals("")) {
            this.memberTel = updateRequestDTO.getMemberTel();
        }
        if(updateRequestDTO.getMemberZipCode() != null && !updateRequestDTO.getMemberZipCode().equals("")) {
            this.memberZipCode = updateRequestDTO.getMemberZipCode();
        }
        if(updateRequestDTO.getMemberAddress() != null && !updateRequestDTO.getMemberAddress().equals("")) {
            this.memberAddress = updateRequestDTO.getMemberAddress();
        }
        if(updateRequestDTO.getMemberDetailAddress() != null && !updateRequestDTO.getMemberDetailAddress().equals("")) {
            this.memberDetailAddress = updateRequestDTO.getMemberDetailAddress();
        }

    }

}
