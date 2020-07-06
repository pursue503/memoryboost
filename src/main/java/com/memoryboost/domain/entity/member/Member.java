package com.memoryboost.domain.entity.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

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
        return this;
    }

}
