package com.memoryboost.domain.entity.product.review;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.member.Role;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @After
    public void after(){
        productReviewRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void findByProductDetailReviewTest(){

        Product product = productRepository.save(Product.builder().productName("갤럭시 GALAX 지포스 RTX 2070 SUPER EX OC D6 8GB PINK Edition").
                productCategory(1)
                .productThumbnail("주소~")
                .productDescription("설명")
                .productPrice(600000)
                .build());

        Member member = memberRepository.save(Member.builder().memberLoginId("abc1234").memberPw("aaa1111").
                memberName("홍길동").
                memberEmail("abc1234@naver.com").
                memberSns("memoryboost").
                memberZipCode("08080").
                memberAddress("서울특별시").
                memberDetailAddress("종로구댕댕댕").
                memberTel("010-0000-0000").
                memberAuth(Role.USER).build());
        productReviewRepository.save(ProductReview.builder().memberId(member).productNo(product).reviewTitle("타이틀").
                reviewContent("내용").reviewGrade(5).build());

        int page = 1;

        List<ProductDetailReviewResponseDTO> reviewResponseDTOList = productReviewRepository.findByProductDetailReview(product,0,page);
        reviewResponseDTOList.forEach(review -> log.info(review.toString()));

        assertThat(5).isEqualTo(reviewResponseDTOList.get(0).getReviewGrade());

    }
}
