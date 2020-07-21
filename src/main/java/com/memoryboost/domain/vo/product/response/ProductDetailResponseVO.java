package com.memoryboost.domain.vo.product.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDetailResponseVO {

    private Long productNo;
    private String productName;
    private Integer productCategory;
    private String productDescription;
    private String productThumbnail;
    private Integer productPrice;
    private List<String> productImagePath;

    private double gradeAvg;

    private List<ProductDetailReviewStarResponseVO> gradeStarList;

}
