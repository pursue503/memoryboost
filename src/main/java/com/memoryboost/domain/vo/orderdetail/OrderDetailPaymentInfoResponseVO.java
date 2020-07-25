package com.memoryboost.domain.vo.orderdetail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderDetailPaymentInfoResponseVO {

    private Long orderTotalAmount;
    private int orderPaymentGb;
    private String bankName;
    private String bankAccountNumber;

}
