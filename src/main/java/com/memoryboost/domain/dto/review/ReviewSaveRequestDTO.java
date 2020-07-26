package com.memoryboost.domain.dto.review;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.review.ProductReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewSaveRequestDTO {

    private Long productNo;
    private String reviewTitle;
    private String reviewContent;
    private int reviewGrade;

    public ProductReview toEntity(Product product , Member member) {
        return ProductReview.builder().productNo(product).memberId(member).reviewTitle(reviewTitle)
                .reviewContent(reviewContent).reviewGrade(reviewGrade).build();
    }

}
