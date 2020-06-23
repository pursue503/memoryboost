package com.memoryboost.domain.entity.email;

import com.memoryboost.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_member_email_auth")
@Entity
public class MemberEmail {

    // 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailNo;

    // 회원아이디
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID",nullable = false)
    private Member memberId;

    // 이메일인증코드
    @Column(nullable = false)
    private String emailCode;

    @Builder
    public  MemberEmail(Member memberId, String emailCode ) {
        this.memberId = memberId;
        this.emailCode = emailCode;
    }

}
