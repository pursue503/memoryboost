package com.memoryboost.service.cart;

import com.memoryboost.domain.dto.cart.request.CartSaveRequestDTO;
import com.memoryboost.domain.entity.cart.CartRepository;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.vo.member.MemberCustomVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Boolean cartSave(CartSaveRequestDTO cartSaveRequestDTO, Authentication authentication) {

        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);
        Product product = productRepository.findById(cartSaveRequestDTO.getProductNo()).orElseThrow(NullPointerException::new);


        return  cartRepository.save(cartSaveRequestDTO.toEntity(product,member)).getCartNo() >=1 ? true : false;
    }

}
