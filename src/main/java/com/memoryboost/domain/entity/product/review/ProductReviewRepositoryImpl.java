package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import com.memoryboost.domain.entity.member.QMember;
import com.memoryboost.domain.entity.product.Product;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductReviewRepositoryImpl implements ProductReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductDetailReviewResponseDTO> findByProductDetailReview(Product product , int order, int page) {
        QMember member = QMember.member;
        QProductReview productReview = QProductReview.productReview;
        //order 0 =  최신 ,  1 = 평점순

        JPAQuery<ProductDetailReviewResponseDTO> jpaQuery = queryFactory.select(Projections.fields(ProductDetailReviewResponseDTO.class,
                member.memberName,productReview.reviewTitle,productReview.reviewContent,productReview.reviewDate,
                productReview.reviewGrade))
                .from(productReview)
                .leftJoin(member).on(productReview.memberId.eq(member))
                .where(productReview.productNo.eq(product));

        if(order == 0) {
            jpaQuery.orderBy(productReview.reviewDate.desc());
        } else {
            jpaQuery.orderBy(productReview.reviewGrade.desc());
        }
        jpaQuery.offset((page - 1) * 10).limit(10);

        return jpaQuery.fetch();
    }

    @Override
    public int countByProductDetailReview(Product product) {

        QProductReview productReview = QProductReview.productReview;

        return (int) queryFactory.select(productReview.count()).from(productReview).where(productReview.productNo.eq(product))
                .fetchCount();
    }
}
