package com.memoryboost.service.review;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.entity.product.review.ProductReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ProductRepository productRepository;
    private final ProductReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<ProductDetailReviewResponseDTO> productDetailReview(Long productNo, int order , int page){

        Product product = productRepository.findById(productNo).orElseThrow(NullPointerException::new);

        List<ProductDetailReviewResponseDTO> reviewResponseDTOList = reviewRepository.findByProductDetailReview(product,order,page);

        int count = reviewRepository.countByProductDetailReview(product) - (page * 10 - 10);

        for (ProductDetailReviewResponseDTO reviewResponseDTO : reviewResponseDTOList) {
            reviewResponseDTO.setNumber(count);
            count--;
        }
        return reviewResponseDTOList;
    }

}
