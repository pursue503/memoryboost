package com.memoryboost.domain.entity.product;

import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.memoryboost.domain.entity.product.review.QProductReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Mod11Check;

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
    public List<ProductSearchResponseDTO> productSearch(String[] searchArr, String order , int page) {

        QProduct product = QProduct.product;
        QProductReview productReview = QProductReview.productReview;

        BooleanBuilder builder = new BooleanBuilder();
        for(String name : searchArr) {
            builder.and(product.productName.contains(name));
        }
        //동적 order by
        JPAQuery<ProductSearchResponseDTO> jpaQuery = queryFactory.select(Projections.constructor(ProductSearchResponseDTO.class,
                product.productNo,product.productName,
                product.productCategory,product.productDescription,
                product.productThumbnail,product.productPrice,
                productReview.reviewGrade.avg(),productReview.reviewNo.count()))
                .from(product)
                .leftJoin(productReview).on(product.eq(productReview.productNo))
                .where(builder)
                .groupBy(product.productNo,product.productName,
                        product.productCategory,product.productDescription,
                        product.productThumbnail,product.productPrice);
        //order 에 따라서 order by 를 다르게
        switch (order.toLowerCase()) {
            case "popular" :
                jpaQuery.orderBy(productReview.reviewGrade.avg().desc());
                break;
            case "pricedesc":
                jpaQuery.orderBy(product.productPrice.desc());
                break;
            case "priceasc" :
                jpaQuery.orderBy(product.productPrice.asc());
                break;
        }

        jpaQuery = jpaQuery.offset((page -1 ) * 10).limit(10);

        List<ProductSearchResponseDTO> responseDTOList = jpaQuery.fetch();

        return responseDTOList;


//        return queryFactory.select(Projections.constructor(ProductSearchResponseDTO.class,
//                product.productNo,product.productName,
//                product.productCategory,product.productDescription,
//                product.productThumbnail,product.productPrice,
//                productReview.reviewGrade.avg().as("reviewGradeAvg"),productReview.reviewNo.count().as("reviewCount")))
//                .from(product)
//                .leftJoin(productReview).on(product.eq(productReview.productNo))
//                .where(builder)
//                .groupBy(product.productNo,product.productName,
//                        product.productCategory,product.productDescription,
//                        product.productThumbnail,product.productPrice)
//                .offset(0)
//                .limit(10)
//                .fetch();
    }

    @Override
    public int countByProductNameContaining(String[] searchArr) {

        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        for(String name : searchArr) {
            builder.and(product.productName.contains(name));
        }

        return (int) queryFactory.select(product.count()).from(product)
                .where(builder).fetchCount();
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
