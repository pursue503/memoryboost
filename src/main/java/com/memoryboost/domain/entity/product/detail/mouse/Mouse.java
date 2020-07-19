package com.memoryboost.domain.entity.product.detail.mouse;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_mouse")
@Entity
public class Mouse {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mouseNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no", nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String mouseCompany;

    // 연결방식
    @Column
    private String mouseConnection;

    @Builder
    public Mouse(Product productNo, String mouseCompany, String mouseConnection) {
        this.productNo = productNo;
        this.mouseCompany = mouseCompany;
        this.mouseConnection = mouseConnection;
    }
}
