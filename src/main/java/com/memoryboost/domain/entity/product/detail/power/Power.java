package com.memoryboost.domain.entity.product.detail.power;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_power")
@Entity
public class Power {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long powerNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no", nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String powerCompany;

    // 파워정격
    @Column
    private String powerWatt;

    @Builder
    public Power(Product productNo, String powerCompany, String powerWatt) {
        this.productNo = productNo;
        this.powerCompany = powerCompany;
        this.powerWatt = powerWatt;
    }
}
