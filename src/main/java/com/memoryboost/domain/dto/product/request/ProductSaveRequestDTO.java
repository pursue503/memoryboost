package com.memoryboost.domain.dto.product.request;

import com.memoryboost.domain.entity.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductSaveRequestDTO {

    //상품을 저장 , 사진을 S3에 업로드

    private String productName;
    private int productCategory;
    private String productDescription;
    private String productThumbnail;
    private int productPrice;

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .productCategory(productCategory)
                .productDescription(productDescription)
                .productThumbnail(productThumbnail)
                .productPrice(productPrice)
                .build();
    }

}
