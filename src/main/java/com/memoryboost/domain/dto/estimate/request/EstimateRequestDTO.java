package com.memoryboost.domain.dto.estimate.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EstimateRequestDTO {

    //견적서 에서 결제로 이동 시킬 때 사용할 DTO
    private Long productNo;
    private int productCnt;

}
