package com.memoryboost.domain.vo.cart.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartProductCountUpdateRequestVO {

    private Long cartNo;
    private int productCnt;

}
