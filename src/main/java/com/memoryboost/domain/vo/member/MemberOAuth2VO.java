package com.memoryboost.domain.vo.member;

import com.memoryboost.domain.entity.member.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class MemberOAuth2VO extends DefaultOAuth2User {
    private static final long serialVersionUID = 1L;

    private Long memberId;
    private String memberName;
    private String memberEmail;
    private boolean memberSt;
    private String memberSns;

    public MemberOAuth2VO(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
        super(authorities, attributes, nameAttributeKey);
    }

    public MemberOAuth2VO(Member member,Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
        super(authorities, attributes, nameAttributeKey);
        this.memberId = member.getMemberId();
        this.memberName = member.getMemberName();
        this.memberEmail = member.getMemberEmail();
        this.memberSt = member.isMemberSt();
        this.memberSns = member.getMemberSns();

    }

}
