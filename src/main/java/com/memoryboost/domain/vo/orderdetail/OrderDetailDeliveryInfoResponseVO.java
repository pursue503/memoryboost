package com.memoryboost.domain.vo.orderdetail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderDetailDeliveryInfoResponseVO {

    private String diNo;
    private String diRecipient;
    private String diTel;
    private String diZipCode;
    private String diAddress;
    private String diDetailAddress;
    private String diComment;

}
