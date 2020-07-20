package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Random;

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


    @Test
    public void dsfasf(){
        log.info("test");
    }

}
