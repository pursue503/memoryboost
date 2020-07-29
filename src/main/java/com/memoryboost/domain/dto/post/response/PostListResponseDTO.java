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
public class PostListResponseDTO {

    private int postNumber;
    private Long postNo;
    private String memberName;
    private String postTitle;
    private String postContent;
    private Date postDate;
    private int postCategory;

}
