package com.memoryboost.domain.dto.product.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailResponseDTO {

    private Long productNo;
    private String productName;
    private Integer productCategory;
    private String productDescription;
    private String productThumbnail;
    private Integer productPrice;
    private List<String> productImagePath;

}
