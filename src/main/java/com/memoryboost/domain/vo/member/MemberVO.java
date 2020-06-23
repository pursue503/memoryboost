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

    //Custom MemberVO
    //Seesion에 들어갈 정보드를 설정
    private Long memberId;
    private String memberName;
    private boolean memberSt;
    private String memberSns;

    private static final long serialVersionUID = 1L;
    public MemberVO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MemberVO(Member member , List<GrantedAuthority> authorities) {
        super(member.getMemberEmail(),member.getMemberPw(),authorities);
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
        this.memberSt = member.isMemberSt();
        this.memberSns = member.getMemberSns();
    }

}
