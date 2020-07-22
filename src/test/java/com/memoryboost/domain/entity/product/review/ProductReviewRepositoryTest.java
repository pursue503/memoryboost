package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

//        Product product = productRepository.findById(1L).orElseThrow(NullPointerException::new);
//
//        List<ProductDetailReviewResponseDTO> reviewResponseDTOList = productReviewRepository.findByProductDetailReview(product);
//        reviewResponseDTOList.forEach(rere -> log.info(rere.toString()));
        log.info("sd");
    }
}
