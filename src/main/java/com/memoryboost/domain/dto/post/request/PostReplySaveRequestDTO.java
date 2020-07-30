package com.memoryboost.domain.dto.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostReplySaveRequestDTO {

    private Long postNo;
    private String postReplyContent;

}
