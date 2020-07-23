package com.memoryboost.domain.vo.cart.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartProductCountUpdateRequestVO {

    private Long cartNo;
    private int productCnt;

}
