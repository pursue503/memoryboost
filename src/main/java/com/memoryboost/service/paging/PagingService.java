package com.memoryboost.service.paging;

import com.memoryboost.domain.dto.product.request.ProductFilterSearchRequestDTO;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.util.paging.PagingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PagingService {

    //상품 repository
    private final ProductRepository productRepository;

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

}
