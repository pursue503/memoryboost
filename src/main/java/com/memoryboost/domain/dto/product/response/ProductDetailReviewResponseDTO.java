package com.memoryboost.domain.dto.product.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDetailReviewResponseDTO {

    private Long number;
    private String memberName;
    private String reviewTitle;
    private String reviewContent;
    private Date reviewDate;
    private Integer reviewGrade;

}
