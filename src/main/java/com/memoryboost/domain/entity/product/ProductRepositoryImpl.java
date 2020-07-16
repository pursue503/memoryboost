package com.memoryboost.domain.entity.product;

import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.memoryboost.domain.entity.product.review.QProductReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.ast.Projection;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public QueryResults<Product> findByProductNameList(List<String> productNameList) {
        QProduct produt = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        for (String s : productNameList) {
            builder.and(produt.productName.contains(s));
        }
        return queryFactory.selectFrom(produt)
                .where(builder)
                .offset(0)
                .limit(10)
                .fetchResults();
    }

    @Override
    public List<ProductSearchResponseDTO> findByProductSearch(String[] searchArr) {

        QProduct product = QProduct.product;
        QProductReview productReview = QProductReview.productReview;

        BooleanBuilder builder = new BooleanBuilder();
        for(String name : searchArr) {
            builder.and(product.productName.contains(name));
        }

        return queryFactory.select(Projections.constructor(ProductSearchResponseDTO.class,
                product.productNo,product.productName,
                product.productCategory,product.productDescription,
                product.productThumbnail,product.productPrice,
                productReview.reviewGrade.avg().as("reviewGradeAvg"),productReview.reviewNo.count().as("reviewCount")))
                .from(product)
                .leftJoin(productReview).on(product.eq(productReview.productNo))
                .where(builder)
                .groupBy(product.productNo,product.productName,
                        product.productCategory,product.productDescription,
                        product.productThumbnail,product.productPrice)
                .offset(0)
                .limit(10)
                .fetch();
    }

    @Override
    public ArrayList<String> searchPreview(String keyword) {
        QProduct product = QProduct.product;
        return (ArrayList<String>) queryFactory.select(product.productName)
                .from(product)
                .where(product.productName.contains(keyword))
                .fetch();
    }
}
