package com.memoryboost.domain.entity.product;

import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.querydsl.core.QueryResults;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepositoryCustom {
    
    //예시
    QueryResults<Product> findByProductNameList(List<String> productNameList);
    
    //상품검색
    List<ProductSearchResponseDTO> productSearch(String[] searchArr , String order , int page);

    //검색한 상품 페이징 총 게시물수
    int countByProductNameContaining(String[] searchArr);

    //검색미리보기
    ArrayList<String> searchPreview(String keyword);



}
