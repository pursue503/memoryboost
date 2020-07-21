package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import com.memoryboost.domain.entity.product.Product;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductReviewRepositoryImpl implements ProductReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductDetailReviewResponseDTO> findByProductDetailReview(Product product) {

        return null;
    }
}
