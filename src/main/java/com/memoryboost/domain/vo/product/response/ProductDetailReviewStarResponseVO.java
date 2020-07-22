package com.memoryboost.domain.vo.product.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDetailReviewStarResponseVO {

    private int reviewGrade;
    private Long gradeCount;

}
