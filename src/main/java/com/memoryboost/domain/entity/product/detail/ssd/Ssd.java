package com.memoryboost.domain.entity.product.detail.ssd;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_ssd")
@Entity
public class Ssd {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ssdNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no",nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String ssdCompany;

    // 스스디용량
    @Column
    private String ssdSize;

    @Builder
    public Ssd(Product productNo, String ssdCompany, String ssdSize) {
        this.productNo = productNo;
        this.ssdCompany = ssdCompany;
        this.ssdSize = ssdSize;
    }
}
