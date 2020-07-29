package com.memoryboost.domain.dto.post.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostRequestDTO {

    private Long postNo;
    private String postTitle;
    private String postContent;
    private String memberName;
    private Long memberId;
    private Date postDate;
    private int postCategory;

}
