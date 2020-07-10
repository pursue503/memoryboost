package com.memoryboost.domain.entity.product;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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

}
