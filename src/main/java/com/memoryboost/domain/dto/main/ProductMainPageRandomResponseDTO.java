package com.memoryboost.domain.dto.main;

import com.memoryboost.domain.entity.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
public class ProductMainPageRandomResponseDTO {

    private Long productNo;
    private String productName;
    private String productDescription;
    private String productThumbnail;
    private int productPrice;

    public ProductMainPageRandomResponseDTO(Product product) {
        this.productNo = product.getProductNo();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productThumbnail = product.getProductThumbnail();
        this.productPrice = product.getProductPrice();
    }

}
