package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import com.memoryboost.domain.entity.product.Product;

import java.util.List;

public interface ProductReviewRepositoryCustom {

    List<ProductDetailReviewResponseDTO> findByProductDetailReview(Product product , int order , int page);

    int countByProductDetailReview(Product product);

}
