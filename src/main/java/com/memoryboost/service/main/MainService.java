package com.memoryboost.service.main;

import com.memoryboost.domain.dto.main.NoticeMainPageResponseDTO;
import com.memoryboost.domain.dto.main.PostMainPageResponseDTO;
import com.memoryboost.domain.dto.main.ProductMainPageRandomResponseDTO;
import com.memoryboost.domain.entity.notice.NoticeRepository;
import com.memoryboost.domain.entity.post.PostRepository;
import com.memoryboost.domain.entity.product.Product;
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
public class MainService {

    private final ProductRepository productRepository;
    private final PostRepository postRepository;
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<ProductMainPageRandomResponseDTO> mainProductRandom() {

        List<Product> productList = productRepository.findByProductRandom();

        List<ProductMainPageRandomResponseDTO> mainPageRandomResponseDTOList = new ArrayList<>();

        for(Product product : productList) {
            mainPageRandomResponseDTOList.add(new ProductMainPageRandomResponseDTO(product));
        }

        return mainPageRandomResponseDTOList;
    }

    @Transactional(readOnly = true)
    public ProductMainPageRandomResponseDTO newProduct() {
        return productRepository.newProduct();
    }

    @Transactional(readOnly = true)
    public List<PostMainPageResponseDTO> newPostReview() {
        return postRepository.mainPagePost();
    }

    @Transactional(readOnly = true)
    public List<NoticeMainPageResponseDTO> noticeMainPage() {
        return noticeRepository.mainPageNoticeAndEventList(1);
    }

    @Transactional(readOnly = true)
    public List<NoticeMainPageResponseDTO> eventMainPage(){
        return noticeRepository.mainPageNoticeAndEventList(3);
    }



}
