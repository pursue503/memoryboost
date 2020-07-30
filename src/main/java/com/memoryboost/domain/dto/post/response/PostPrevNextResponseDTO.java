package com.memoryboost.domain.dto.post.response;

import com.memoryboost.domain.vo.post.PostPrevNextVO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostPrevNextResponseDTO {

    private PostPrevNextVO prev;
    private PostPrevNextVO next;

}
