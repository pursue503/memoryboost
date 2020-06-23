package com.memoryboost.domain.entity.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_product_image")
@Entity
public class ProductImage {

    // 상품이미지번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no",nullable = false)
    private Product productNo;

    // 이미지 경로
    @Column
    private String productImagePath;

    @Builder
    public ProductImage(Product productNo, String productImagePath) {
        this.productNo = productNo;
        this.productImagePath = productImagePath;
    }
}
