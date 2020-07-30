package com.memoryboost.domain.dto.post.response;

import com.memoryboost.domain.entity.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostReplyListResponseDTO {

    private Long postReplyNo;
    private Long memberId;
    private String memberName;
    private String postReplyContent;
    private Date postReplyDate;

}
