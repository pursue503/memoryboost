package com.memoryboost.domain.vo.kakao;

import lombok.Data;

@Data
public class KaKaoPayApprovalVO {

    private String tid; //결제 고유 번호

    private String partner_order_id; //가맹점 주문 번호
    private String partner_user_id; //가맹점 user Id
    private String payment_method_type; //결제 수단, CARD 또는 MONEY 중 하나

}
