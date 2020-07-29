package com.memoryboost.domain.dto.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostUpdateRequestDTO {

    private Long postNo;
    private String postTitle;
    private String postContent;

}
