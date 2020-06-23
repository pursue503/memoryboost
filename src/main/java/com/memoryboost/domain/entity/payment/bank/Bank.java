package com.memoryboost.domain.entity.payment.bank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_bank")
@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankNo;

    //은행이름
    @Column(nullable = false)
    private String bankName;

    //은행계좌번호
    @Column(nullable = false)
    private String bankAccountNumber;

    @Builder
    public Bank(String bankName, String bankAccountNumber) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }
}
