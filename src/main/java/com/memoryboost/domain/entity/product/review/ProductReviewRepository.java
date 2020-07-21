package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import com.memoryboost.domain.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview,Long> , ProductReviewRepositoryCustom {
}
