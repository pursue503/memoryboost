package com.memoryboost.domain.entity.product.detail.tbcase;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_case")
@Entity
public class Case {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caseNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no", nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String caseCompany;

    // 스스디용량
    @Column
    private String caseSize;

    @Builder
    public Case(Product productNo, String caseCompany, String caseSize) {
        this.productNo = productNo;
        this.caseCompany = caseCompany;
        this.caseSize = caseSize;
    }
}
