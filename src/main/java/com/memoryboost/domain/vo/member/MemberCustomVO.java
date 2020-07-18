package com.memoryboost.domain.vo.member;

import com.memoryboost.domain.entity.member.Member;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.*;

@Getter
public class MemberCustomVO implements UserDetails, CredentialsContainer, OAuth2User , Serializable {

    private Long memberId;
    private String memberLoginId;
    private String memberPw;
    private String memberEmail;
    private String memberName;
    private String memberTel;
    private boolean memberSt;
    private String memberSns;
    private String memberZipCode;
    private String memberAddress;
    private String memberDetailAddress;

    private final Set<GrantedAuthority> authorities;

    //sns
    private Map<String, Object> attributes;
    private String nameAttributeKey;

    public MemberCustomVO(Member member, List<GrantedAuthority> authorities) {
        this.memberId = member.getMemberId();
        this.memberLoginId = member.getMemberLoginId();
        this.memberPw = member.getMemberPw();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.memberTel = member.getMemberTel();
        this.memberSt = member.isMemberSt();
        this.memberSns = member.getMemberSns();
        this.memberZipCode = member.getMemberZipCode();
        this.memberAddress = member.getMemberAddress();
        this.memberDetailAddress = member.getMemberDetailAddress();
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(authorities)));
    }

    public MemberCustomVO(Member member,Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(authorities)));
        this.attributes = Collections.unmodifiableMap(new LinkedHashMap<>(attributes));
        this.nameAttributeKey = nameAttributeKey;

        this.memberId = member.getMemberId();
        this.memberEmail = member.getMemberEmail();
        this.memberName = member.getMemberName();
        this.memberTel = member.getMemberTel();
        this.memberSt = member.isMemberSt();
        this.memberSns = member.getMemberSns();
        this.memberZipCode = member.getMemberZipCode();
        this.memberAddress = member.getMemberAddress();
        this.memberDetailAddress = member.getMemberDetailAddress();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return memberPw;
    }

    @Override
    public String getUsername() {
        return memberName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    //비밀번호를 지워줌
    @Override
    public void eraseCredentials() {
        memberPw = null;
    }


    //SNS
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    /*
     nameAttributeKey -> OAuthAttributesDTO에서  nameAttributeKey 를 id로 줬고
     그 id값에 kakao 에서 준 id값을 넣어줘서 principal.name 을 하면 id값이 나오게됨.
     */
    @Override
    public String getName() {
        return this.getAttributes().get(this.nameAttributeKey).toString();
    }

    private Set<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        SortedSet<GrantedAuthority> sortedAuthorities =
                new TreeSet<>(Comparator.comparing(GrantedAuthority::getAuthority));
        sortedAuthorities.addAll(authorities);
        return sortedAuthorities;
    }

}
