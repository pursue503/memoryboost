package com.memoryboost.domain.vo.orderdetail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderDetailProductResponseVO {

    //주문 한 상품들 정보.
    private Long productNo;
    private String productName;
    private int productPrice;
    private int productCnt;


}
