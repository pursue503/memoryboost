package com.memoryboost.domain.vo.order.response;

import lombok.*;

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

    @Builder
    public OrderPaymentResponseVO(Long productNo, String productName, String productThumbnail, int productCnt, int productPrice) {
        this.productNo = productNo;
        this.productName = productName;
        this.productThumbnail = productThumbnail;
        this.productCnt = productCnt;
        this.productPrice = productPrice;
    }
}
