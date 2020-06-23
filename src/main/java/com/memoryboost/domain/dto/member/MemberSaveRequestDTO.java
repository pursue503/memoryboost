package com.memoryboost.domain.dto.member;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveRequestDTO {

    private String memberLoginId;
    private String memberPw;
    private String memberName;
    private String memberTel;
    private String memberZipCode;
    private String memberAddress;
    private String memberDetailAddress;
    private String memberEmail;

    @Builder
    public MemberSaveRequestDTO(String memberLoginId, String memberPw, String memberName,
                                String memberTel, String memberZipCode, String memberAddress,
                                String memberDetailAddress, String memberEmail) {
        this.memberLoginId = memberLoginId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberTel = memberTel;
        this.memberZipCode = memberZipCode;
        this.memberAddress = memberAddress;
        this.memberDetailAddress = memberDetailAddress;
        this.memberEmail = memberEmail;
    }

    public Member toEntity(){
        return Member.builder()
                .memberLoginId(memberLoginId)
                .memberPw(memberPw)
                .memberName(memberName)
                .memberTel(memberTel)
                .memberZipCode(memberZipCode)
                .memberAddress(memberAddress)
                .memberDetailAddress(memberDetailAddress)
                .memberSns("memoryboost")
                .memberAuth(Role.USER)
                .memberEmail(memberEmail)
                .build();
    }


}
