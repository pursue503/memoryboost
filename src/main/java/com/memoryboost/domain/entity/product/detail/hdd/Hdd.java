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
    private Long hddNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_No",nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String hddCompany;

    //하드디스크 용량
    @Column
    private String hddSize;

    @Builder
    public Hdd(Product productNo, String hddCompany, String hddSize) {
        this.productNo = productNo;
        this.hddCompany = hddCompany;
        this.hddSize = hddSize;
    }
}
