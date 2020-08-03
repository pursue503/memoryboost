package com.memoryboost.domain.entity.order;

import com.memoryboost.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_order")
@Entity
public class Order {

    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNo;

    //회원아이디
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    //주문날짜
    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date orderDate;

    //주문상태
    @Column(nullable = false)
    private int orderSt;

    //결제방식 0 , 무통장 1, 카카오페이
    @Column(nullable = false)
    private int orderPaymentGb;

    //운송장번호
    @Column
    private Long orderTrackingNumber;

    @Column
    private Long orderTotalAmount;

    @Builder
    public Order(Member member, int orderSt, int orderPaymentGb, Long orderTrackingNumber, Long orderTotalAmount) {
        this.member = member;
        this.orderSt = orderSt;
        this.orderPaymentGb = orderPaymentGb;
        this.orderTrackingNumber = orderTrackingNumber;
        this.orderTotalAmount = orderTotalAmount;
    }

    public void orderStUpdate(){

        this.orderSt++;

    }

}
