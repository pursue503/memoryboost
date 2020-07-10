package com.memoryboost.domain.dto.member.memoryboost.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberUpdateRequestDTO {

    private String memberPw;
    private String memberName;
    private String memberTel;
    private String memberZipCode;
    private String memberAddress;
    private String memberDetailAddress;




}
