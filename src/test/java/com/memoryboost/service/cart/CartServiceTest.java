package com.memoryboost.service.cart;

import com.memoryboost.domain.entity.cart.Cart;
import com.memoryboost.domain.entity.cart.CartRepository;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.member.Role;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.vo.cart.request.CartProductCountUpdateRequestVO;
import com.memoryboost.service.cart.CartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CartServiceTest {

    @Autowired
    private CartService cartService;

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
    public void memberCartProductCountUpdateTests(){

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
        Cart cart = cartRepository.save(Cart.builder().product(product).member(member).productCnt(5).build());

        int count = 10;

        CartProductCountUpdateRequestVO cartProductCountUpdateRequestVO = new CartProductCountUpdateRequestVO();
        cartProductCountUpdateRequestVO.setCartNo(cart.getCartNo());
        cartProductCountUpdateRequestVO.setProductCnt(count);

        cartService.cartCountUpdate(cartProductCountUpdateRequestVO);

        Cart resultCart = cartRepository.findById(cart.getCartNo()).orElseThrow(NullPointerException::new);

        assertThat(count).isEqualTo(resultCart.getProductCnt());
    }

}
