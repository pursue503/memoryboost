package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_product_review_like")
@Entity
public class ProductReviewLike {

    // 좋아요번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeNo;

    // 리뷰번호
    @ManyToOne
    @JoinColumn(name = "review_no",nullable = false)
    private ProductReview reviewNo;

    // 좋아요 누른 회원
    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private Member memberId;

    @Builder
    public ProductReviewLike(ProductReview reviewNo, Member memberId) {
        this.reviewNo = reviewNo;
        this.memberId = memberId;
    }
}
