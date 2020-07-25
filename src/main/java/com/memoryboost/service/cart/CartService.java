package com.memoryboost.service.cart;

import com.memoryboost.domain.dto.cart.request.CartSaveRequestDTO;
import com.memoryboost.domain.entity.cart.Cart;
import com.memoryboost.domain.entity.cart.CartRepository;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.vo.cart.request.CartProductCountUpdateRequestVO;
import com.memoryboost.domain.vo.cart.response.MemberCartResponseVO;
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
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Boolean cartSave(List<CartSaveRequestDTO> cartSaveRequestDTOList, Authentication authentication) {

        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);
        for(CartSaveRequestDTO cart : cartSaveRequestDTOList) {
            Product product = productRepository.findById(cart.getProductNo()).orElseThrow(NullPointerException::new);
            cartRepository.save(cart.toEntity(product,member));
        }
        return  true;
    }

    @Transactional(readOnly = true)
    public List<MemberCartResponseVO> memberCart(Authentication authentication) {

        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);

        return cartRepository.findByMemberCart(member);
    }

    @Transactional
    public void cartCountUpdate(CartProductCountUpdateRequestVO updateRequestVO) {
        Cart cart = cartRepository.findById(updateRequestVO.getCartNo()).orElseThrow(NullPointerException::new);
        cart.cartProductCountUpdate(updateRequestVO);
    }

    @Transactional
    public void memberCartDelete(Long cartNo, Authentication authentication) {

        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);

        Cart cart = cartRepository.findByCartNoAndMemberId(cartNo,member);

        cartRepository.delete(cart);
    }


}
