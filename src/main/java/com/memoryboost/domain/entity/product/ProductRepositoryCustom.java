package com.memoryboost.domain.entity.product;

import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.querydsl.core.QueryResults;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepositoryCustom {

    QueryResults<Product> findByProductNameList(List<String> productNameList);

    List<ProductSearchResponseDTO> findByProductSearch(String[] searchArr);

    ArrayList<String> productSearch(String keyword);

}
