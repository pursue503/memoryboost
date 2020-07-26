package com.memoryboost.service.review;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import com.memoryboost.domain.dto.review.ReviewSaveRequestDTO;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.entity.product.review.ProductReviewRepository;
import com.memoryboost.domain.vo.member.MemberCustomVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ProductRepository productRepository;
    private final ProductReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    //상품 상세 페이지 리뷰가져오기.
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

    @Transactional
    public boolean productReviewSave(ReviewSaveRequestDTO reviewSaveRequestDTO, Authentication authentication) {
//        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
//        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);
        Member member = memberRepository.findById(2L).orElseThrow(NullPointerException::new);
        Product product = productRepository.findById(reviewSaveRequestDTO.getProductNo()).orElseThrow(NullPointerException::new);

        reviewRepository.save(reviewSaveRequestDTO.toEntity(product,member));
        return true;

    }

}
