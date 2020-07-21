package com.memoryboost.domain.entity.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_product")
@Entity
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNo;

    // 상품이름
    @Column(nullable = false)
    private String productName;

    // 상품카테고리
    @Column(nullable = false)
    private int productCategory;

    // 상품설명
    @Column(nullable = false)
    private String productDescription;

    // 상품사진
    @Column(nullable = false)
    private String productThumbnail;

    // 가격
    @Column(nullable = false)
    private int productPrice;

    @Builder
    public Product(String productName, int productCategory, String productDescription,
                   String productThumbnail, int productPrice) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDescription = productDescription;
        this.productThumbnail = productThumbnail;
        this.productPrice = productPrice;
    }

}
