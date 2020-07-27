package com.memoryboost.service.paging;

import com.memoryboost.domain.dto.product.request.ProductFilterSearchRequestDTO;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.order.OrderRepository;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.entity.product.review.ProductReviewRepository;
import com.memoryboost.domain.vo.member.MemberCustomVO;
import com.memoryboost.util.paging.PagingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PagingService {

    //상품 repository
    private final ProductRepository productRepository;
    private final ProductReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public PagingUtil searchPaging(String keyword, int page) {

        String[] searchArr = keyword.split(" ");

        PagingUtil paging = new PagingUtil();
        //총 게시물 수
        paging.setTotalResult(productRepository.countByProductNameContaining(searchArr));
        //현재 페이지
        paging.setPage(page);
        paging.pageSetting();
        return paging;
    }

    @Transactional(readOnly = true)
    public PagingUtil filterSearchPaging(ProductFilterSearchRequestDTO filterDTO, int page) {

        PagingUtil paging= new PagingUtil();
        paging.setTotalResult(productRepository.countByFilterSearch(filterDTO));

        paging.setPage(page);
        paging.pageSetting();
        return paging;
    }

    @Transactional(readOnly = true)
    public PagingUtil productDetailReviewPaging(Long productNo,int page) {

        Product product = productRepository.findById(productNo).orElseThrow(NullPointerException::new);

        PagingUtil paging = new PagingUtil();
        paging.setTotalResult(reviewRepository.countByProductDetailReview(product));
        paging.setPage(page);
        paging.pageSetting();
        return paging;
    }

    @Transactional(readOnly = true)
    public PagingUtil memberOrderListPaging(Authentication authentication , int page) {
        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);

        PagingUtil paging = new PagingUtil();
        paging.setTotalResult(orderRepository.findByMemberOrderPaging(member));
        paging.setPage(page);
        paging.pageSetting();
        return paging;
    }

    @Transactional(readOnly = true)
    public PagingUtil estimateFilterSearchPaging(ProductFilterSearchRequestDTO filterDTO, String keyword, int page) {

        PagingUtil paging = new PagingUtil();
        paging.setTotalResult(productRepository.countByEstimateFilterSearch(filterDTO,keyword));
        paging.setPage(page);
        paging.pageSetting();
        return paging;

    }

}
