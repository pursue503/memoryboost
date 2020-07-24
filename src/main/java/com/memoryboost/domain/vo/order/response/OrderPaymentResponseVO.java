package com.memoryboost.domain.vo.order.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderPaymentResponseVO {

    private Long cartNo;
    private Long productNo;
    private String productName;
    private String productThumbnail;
    private int productCnt;
    private int productPrice;
}
