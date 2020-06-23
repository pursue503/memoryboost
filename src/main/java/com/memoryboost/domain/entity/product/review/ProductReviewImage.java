package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_product_review_image")
@Entity
public class ProductReviewImage {

    // 리뷰사진번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewImageNo;

    // 리뷰번호
    @ManyToOne
    @JoinColumn(name = "review_no",nullable = false)
    private ProductReview reviewNo;

    // 이미지 경로
    @Column(nullable = false)
    private String reviewImagePath;

    @Builder
    public ProductReviewImage(ProductReview reviewNo, String reviewImagePath) {
        this.reviewNo = reviewNo;
        this.reviewImagePath = reviewImagePath;
    }
}
