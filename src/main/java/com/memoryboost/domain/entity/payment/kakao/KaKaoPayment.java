package com.memoryboost.domain.entity.payment.kakao;

import com.memoryboost.domain.entity.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_kakao_payment")
@Entity
public class KaKaoPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kakaoNo;

    @ManyToOne
    @JoinColumn(name = "ORDER_NO", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String tid;

    @Builder
    public KaKaoPayment(Order order, String tid) {
        this.order = order;
        this.tid = tid;
    }
}
