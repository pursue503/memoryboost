package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductReviewRepositoryTest {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JPAQueryFactory queryFactory;



    @Test
    public void findByProductDetailReviewTest(){

        Product product = productRepository.findById(3L).orElseThrow(NullPointerException::new);

        int page = 1;

        List<ProductDetailReviewResponseDTO> reviewResponseDTOList = productReviewRepository.findByProductDetailReview(product,0,page);
        reviewResponseDTOList.forEach(review -> log.info(review.toString()));


        int count = productReviewRepository.countByProductDetailReview(product) - (page * 10 - 10);

        for(ProductDetailReviewResponseDTO review : reviewResponseDTOList) {
            review.setNumber(count);
            count--;
        }

        reviewResponseDTOList.forEach(review -> log.info(review.toString()));

    }
}
