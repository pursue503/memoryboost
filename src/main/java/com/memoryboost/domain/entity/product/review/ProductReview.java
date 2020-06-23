package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_product_review")
@Entity
public class ProductReview {

    // 리뷰번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no",nullable = false)
    private Product productNo;

    // 작성자아이디
    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private Member memberId;

    // 제목
    @Column(nullable = false)
    private String reviewTitle;

    // 내용
    @Column(nullable = false)
    private String reviewContent;

    // 작성날짜
    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date reviewDate;

    // 점수
    @Column(nullable = false)
    private int reviewGrade;

    @Builder
    public ProductReview(Product productNo, Member memberId, String reviewTitle, String reviewContent, int reviewGrade) {
        this.productNo = productNo;
        this.memberId = memberId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewGrade = reviewGrade;
    }
}
