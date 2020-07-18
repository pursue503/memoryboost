package com.memoryboost.service.product;

import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.memoryboost.domain.entity.product.ProductRepository;
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

    @Transactional(readOnly = true)
    public List<ProductSearchResponseDTO> productSearch(String keyword, String order, int page) {
        String[] searchArr = keyword.split(" "); // 배열로 변환
        return productRepository.productSearch(searchArr,order,page);
    }

}
