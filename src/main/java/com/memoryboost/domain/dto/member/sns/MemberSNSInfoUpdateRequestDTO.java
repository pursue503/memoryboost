package com.memoryboost.domain.dto.member.sns;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberSNSInfoUpdateRequestDTO {

    private String memberEmail;
    private String memberTel;
    private String memberZipCode;
    private String memberAddress;
    private String memberDetailAddress;

}
