package com.memoryboost.domain.dto.order.request;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.order.DeliveryInformation;
import com.memoryboost.domain.entity.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderSaveRequestDTO {

    //주문 상세 정보와 주문관련된걸 한번에받음 , 카트번호는 따로.
    private int orderPaymentGb;
    private Long orderTotalAmount;
    private Long bankNo;

    private String diRecipient;
    private String diTel;
    private String diZipCode;
    private String diAddress;
    private String diDetailAddress;
    private String diComment;

    public Order orderEntity(Member member) {
        if(orderPaymentGb == 0) { // 0 카카오 , 1 무통
            return Order.builder()
                    .member(member)
                    .orderSt(2)
                    .orderPaymentGb(orderPaymentGb)
                    .orderTotalAmount(orderTotalAmount).build();
        } else {
            return Order.builder()
                    .member(member)
                    .orderSt(1)
                    .orderPaymentGb(orderPaymentGb)
                    .orderTotalAmount(orderTotalAmount).build();
        }
    }

    public DeliveryInformation deliveryInformationEntity(Order order) {
        return DeliveryInformation.builder()
                .order(order)
                .diRecipients(diRecipient)
                .diTel(diTel)
                .diZipCode(diZipCode)
                .diAddress(diAddress)
                .diDetailAddress(diDetailAddress)
                .diComment(diComment)
                .build();
    }

}
