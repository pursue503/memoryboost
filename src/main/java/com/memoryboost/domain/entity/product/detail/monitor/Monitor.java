package com.memoryboost.domain.entity.product.detail.monitor;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_monitor")
@Entity
public class Monitor {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long monitorNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no",nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String monitorCompany;

    // 패널
    @Column
    private String monitorPanel;

    @Builder
    public Monitor(Product productNo, String monitorCompany, String monitorPanel) {
        this.productNo = productNo;
        this.monitorCompany = monitorCompany;
        this.monitorPanel = monitorPanel;
    }
}
