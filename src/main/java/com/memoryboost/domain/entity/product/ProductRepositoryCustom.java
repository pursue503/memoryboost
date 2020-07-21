package com.memoryboost.domain.entity.product;

import com.memoryboost.domain.dto.product.request.ProductFilterSearchRequestDTO;
import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.memoryboost.domain.vo.product.response.ProductDetailResponseVO;
import com.querydsl.core.QueryResults;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepositoryCustom {
    
    //예시
    QueryResults<Product> findByProductNameList(List<String> productNameList);
    
    //상품검색
    List<ProductSearchResponseDTO> productSearch(String[] searchArr , String order , int page);

    //검색한 상품 페이징 총 게시물 수
    int countByProductNameContaining(String[] searchArr);

    //검색미리보기
    ArrayList<String> searchPreview(String keyword);

    //필터검색
    List<ProductSearchResponseDTO> productFilterSearch(ProductFilterSearchRequestDTO filterDTO, String order , int page);

    //필터 검색 페이징 을 위한 총 게시물 수
    int countByFilterSearch(ProductFilterSearchRequestDTO filterDTO);

    ProductDetailResponseVO productDetail(Product productEntity);



}
