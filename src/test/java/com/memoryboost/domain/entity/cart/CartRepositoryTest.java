package com.memoryboost.domain.entity.cart;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.member.Role;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.vo.cart.response.MemberCartResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @After
    public void after(){
        cartRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void memberCartTest() {

        Product product = productRepository.save(Product.builder().productName("갤럭시 GALAX 지포스 RTX 2070 SUPER EX OC D6 8GB PINK Edition").
                productCategory(1)
                .productThumbnail("썸네일경로")
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
        cartRepository.save(Cart.builder().product(product).member(member).productCnt(5).build());

        List<MemberCartResponseVO> memberCartResponseVOList = cartRepository.findByMemberCart(member);

        assertThat("썸네일경로").isEqualTo(memberCartResponseVOList.get(0).getProductThumbnail());

    }

    @Test
    public void findByCartNoAndMemberIdTest() {


        Product product = productRepository.save(Product.builder().productName("갤럭시 GALAX 지포스 RTX 2070 SUPER EX OC D6 8GB PINK Edition").
                productCategory(1)
                .productThumbnail("썸네일경로")
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
        Cart cartEntity = cartRepository.save(Cart.builder().product(product).member(member).productCnt(5).build());

        Cart cart = cartRepository.findByCartNoAndMemberId(cartEntity.getCartNo(), member);
        assertThat(cartEntity.getCartNo()).isEqualTo(cart.getCartNo());
    }

}
