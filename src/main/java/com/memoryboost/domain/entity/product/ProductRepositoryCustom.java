package com.memoryboost.domain.entity.product;

import com.querydsl.core.QueryResults;

import java.util.List;

public interface ProductRepositoryCustom {

    QueryResults<Product> findByProductNameList(List<String> productNameList);

}
