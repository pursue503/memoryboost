package com.memoryboost.domain.dto.member.memoryboost.response;

import com.memoryboost.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class MemberFindByLoginIdResponseDTO {

    private String memberLoginId;
    private Date memberSignupDatel;

    public MemberFindByLoginIdResponseDTO(Member member) {
        this.memberLoginId = member.getMemberLoginId();
        this.memberSignupDatel = member.getMemberSignupDate();
    }
}
