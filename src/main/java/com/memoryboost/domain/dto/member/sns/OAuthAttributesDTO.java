package com.memoryboost.domain.dto.member.sns;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributesDTO {

    //구글에서 오는값
    private Map<String,Object> attributes;
    private String nameAttributeKey;

    //내 서비스 Entity값
    private String memberEmail;
    private String memberName;

    //서비스명, google , naver, kako
    private String registrationId;

    @Builder
    public OAuthAttributesDTO(Map<String,Object> attributes, String nameAttributeKey, String memberEmail
            , String memberName, String registrationId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.memberEmail = memberEmail;
        this.memberName = memberName;
        this.registrationId = registrationId;
    }

    // 1. OAuth2USer에서 반환하는 사용자 정보는 Map 이기 때문에 값 하나하나를 변환해야만함
    public static OAuthAttributesDTO of(String registrationId,String userNameAttributeName,
                                     Map<String,Object> attributes) {
        registrationId = registrationId.toLowerCase();
        if("naver".equals(registrationId)) {
            return ofNaver("id",attributes,registrationId);
        } else if("kakao".equals(registrationId)) {
            return ofKakao("id",attributes,registrationId);
        }

        return ofGoogle(userNameAttributeName, attributes,registrationId);
    }

    private static OAuthAttributesDTO ofGoogle(String userNameAttributeName, Map<String,Object> attributes,
                                               String registrationId) {
        return OAuthAttributesDTO.builder()
                .memberEmail((String) attributes.get("email"))
                .memberName((String) attributes.get("name"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributesDTO ofNaver(String userNameAttributeName,Map<String,Object> attributes,
                                              String registrationId) {
        Map<String,Object> response = (Map<String, Object>) attributes.get("response"); //네이버 JSON key값

        return OAuthAttributesDTO.builder()
                .memberEmail((String) response.get("email"))
                .memberName((String) response.get("name"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributesDTO ofKakao(String userNameAttributeName, Map<String,Object> attributes,
                                              String registrationId) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account"); //전체값에서 kakao_account 이메일, profile
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile"); // 위에서 profile 닉네임이 들어있음
        profile.put("email",kakaoAccount.get("email")); //email값 넣어놓기
        profile.put("id", attributes.get("id")); // id값 설정 카카오는 값들이 분산 되어있음. 필요한 값들을 한곳에 몰아줌.
        System.out.println(userNameAttributeName);
        System.out.println(attributes.get("id"));
        return OAuthAttributesDTO.builder()
                .memberEmail((String) profile.get("email"))
                .memberName((String) profile.get("nickname"))
                .attributes(profile)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    // User 엔티티를 생성 , OAuthAttributes 에서 엔티티를 생성하는 시점은 처음 가입할때
    // 외부 로그인이기 때문에 권한을GUEST를주고 추후 정보 입력하면 회원으로 업데이트
    public Member toEntity(){
        return Member.builder()
                .memberEmail(memberEmail)
                .memberName(memberName)
                .memberAuth(Role.GUEST)
                .memberSns(registrationId) // 외부로그인
                .build();
    }

}
