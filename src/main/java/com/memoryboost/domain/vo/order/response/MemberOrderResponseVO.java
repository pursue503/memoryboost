package com.memoryboost.domain.vo.order.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MemberOrderResponseVO {

    private Long orderNo;
    private Date orderDate;
    private String orderSt;
    private Long orderTrackingNumber;
    private Long orderAmountTotal;
}