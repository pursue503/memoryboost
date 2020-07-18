package com.memoryboost.domain.vo.member;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberVO extends User {

    //사용안함.

    //Custom MemberVO
    //Seesion에 들어갈 정보를 설정
    private Long memberId;
    private String memberLoginId;
    private String memberEmail;
    private String memberName;
    private String memberTel;
    private boolean memberSt;
    private String memberSns;
    private String memberZipCode;
    private String memberAddress;
    private String memberDetailAddress;


    private static final long serialVersionUID = 1L;
    public MemberVO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MemberVO(Member member , List<GrantedAuthority> authorities) {
        super(member.getMemberLoginId(),member.getMemberPw(),authorities);
        this.memberId = member.getMemberId();
        this.memberLoginId = member.getMemberLoginId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.memberTel = member.getMemberTel();
        this.memberSt = member.isMemberSt();
        this.memberSns = member.getMemberSns();
        this.memberZipCode = member.getMemberZipCode();
        this.memberAddress = member.getMemberAddress();
        this.memberDetailAddress = member.getMemberDetailAddress();
    }

}
