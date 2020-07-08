package com.memoryboost.domain.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberSNSInfoUpdateRequestDTO {

    private String memberTel;
    private String memberZipCode;
    private String memberAddress;
    private String memberDetailAddress;

    @Builder
    public MemberSNSInfoUpdateRequestDTO(String memberTel, String memberZipCode, String memberAddress, String memberDetailAddress) {
        this.memberTel = memberTel;
        this.memberZipCode = memberZipCode;
        this.memberAddress = memberAddress;
        this.memberDetailAddress = memberDetailAddress;
    }
}
