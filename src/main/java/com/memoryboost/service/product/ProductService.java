package com.memoryboost.service.product;

import com.memoryboost.domain.dto.product.request.ProductFilterSearchRequestDTO;
import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.vo.product.response.ProductDetailResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    //미리보기
    @Transactional(readOnly = true)
    public ArrayList<String> productSearchPreview(String keyword) {
        return productRepository.searchPreview(keyword);
    }
    
    //검색 한 결과 전달
    @Transactional(readOnly = true)
    public List<ProductSearchResponseDTO> productSearch(String keyword, String order, int page) {

        if(keyword.trim().equals("")) {
            List<ProductSearchResponseDTO> productSearchResponseDTOList = new ArrayList<>();
            return productSearchResponseDTOList;
        }
        String[] searchArr = keyword.split(" "); // 배열로 변환
        return productRepository.productSearch(searchArr,order,page);
    }
    
    //필터 검색 결과 전달
    @Transactional(readOnly = true)
    public List<ProductSearchResponseDTO> filterSearch(ProductFilterSearchRequestDTO filterDTO, String order, int page) {
        return productRepository.productFilterSearch(filterDTO,order,page);
    }

    //상품 결과 값 + 이미지 리스트 전송.
    @Transactional(readOnly = true)
    public ProductDetailResponseVO productDetail(Long productNo) {
        Product product = productRepository.findById(productNo).orElseThrow(NullPointerException::new);
        return productRepository.productDetail(product);
    }

    @Transactional(readOnly = true)
    public List<ProductSearchResponseDTO> estimateFilterSearch(ProductFilterSearchRequestDTO filterDTO, String order, int page , String keyword) {

        return productRepository.estimateFilterSearch(filterDTO,order,page,keyword);
    }

}
