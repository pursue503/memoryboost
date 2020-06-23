package com.memoryboost.domain.entity.product.detail.hdd;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_hdd")
@Entity
public class Hdd {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long motherboardNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_No",nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String metherboardCompany;

    // 소켓
    @Column
    private String metherboardSocket;

    @Builder
    public Hdd(Product productNo, String metherboardCompany, String metherboardSocket) {
        this.productNo = productNo;
        this.metherboardCompany = metherboardCompany;
        this.metherboardSocket = metherboardSocket;
    }
}
