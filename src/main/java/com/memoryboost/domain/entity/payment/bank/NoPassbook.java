package com.memoryboost.domain.entity.payment.bank;

import com.memoryboost.domain.entity.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_no_passbook")
@Entity
public class NoPassbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passbookNo;

    @ManyToOne
    @JoinColumn(name = "ORDER_NO",nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "BANK_NO", nullable = false)
    private Bank bank;

    @Builder
    public NoPassbook(Order order, Bank bank) {
        this.order = order;
        this.bank = bank;
    }
}
