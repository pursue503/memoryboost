package com.memoryboost.domain.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProductSearchResponseDTO {

    private Long productNo;
    private String productName;
    private int productCategory;
    private String productDescription;
    private String productThumbnail;
    private int productPrice;
    private double reviewGradeAvg; // null 을 처리하기위해 Double
    private Long reviewCount;


}
