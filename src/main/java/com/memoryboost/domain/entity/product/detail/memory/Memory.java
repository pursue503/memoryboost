package com.memoryboost.domain.entity.product.detail.memory;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_memory")
@Entity
public class Memory {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoryNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "productNo",nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String memoryCompany;

    // 제품용량
    @Column
    private String memorySize;

    @Builder

    public Memory(Product productNo, String memoryCompany, String memorySize) {
        this.productNo = productNo;
        this.memoryCompany = memoryCompany;
        this.memorySize = memorySize;
    }
}
