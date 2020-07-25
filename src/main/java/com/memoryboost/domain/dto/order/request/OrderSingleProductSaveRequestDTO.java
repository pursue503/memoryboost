package com.memoryboost.domain.dto.order.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderSingleProductSaveRequestDTO {

    private Long productNo;
    private int productCnt;

}
