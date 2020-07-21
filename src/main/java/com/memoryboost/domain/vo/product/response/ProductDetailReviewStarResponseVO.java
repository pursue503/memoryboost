package com.memoryboost.domain.vo.product.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDetailReviewStarResponseVO {

    private Integer reviewGrade;
    private Long gradeCount;

}
