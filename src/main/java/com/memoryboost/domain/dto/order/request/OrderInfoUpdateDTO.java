package com.memoryboost.domain.dto.order.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderInfoUpdateDTO {

    private Long orderNo;
    private String diRecipients;
    private String diTel;
    private String diZipCode;
    private String diAddress;
    private String diDetailAddress;
    private String diComment;

}
