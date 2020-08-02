package com.memoryboost.domain.dto.notice.response;

import com.memoryboost.domain.vo.notice.NoticePrevNextVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class NoticePrevNextResponseDTO {

    private NoticePrevNextVO prev;
    private NoticePrevNextVO next;

}
