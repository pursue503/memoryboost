package com.memoryboost.domain.dto.post.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostPrevNextResponseDTO {

    private Long prev;
    private Long next;

}
