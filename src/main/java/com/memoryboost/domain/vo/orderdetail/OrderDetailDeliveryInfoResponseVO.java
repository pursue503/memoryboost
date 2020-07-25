package com.memoryboost.domain.vo.orderdetail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailDeliveryInfoResponseVO {

    private String diRecipient;
    private String diTel;
    private String diZipCode;
    private String diAddress;
    private String diDetailAddress;
    private String diComment;

}
