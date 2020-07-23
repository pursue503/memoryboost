package com.memoryboost.controller.cart;

import com.memoryboost.domain.vo.cart.request.CartProductCountUpdateRequestVO;
import com.memoryboost.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartMoveController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String memberCartList(Authentication authentication , Model model){

        model.addAttribute("cartList", cartService.memberCart(authentication));
        return "mypage/cartlist";
    }

    @PutMapping("/cart")
    public String memberCartProductCountUpdate(CartProductCountUpdateRequestVO updateRequestVO) {
        try{
            cartService.cartCountUpdate(updateRequestVO);
            return "redirect:/cart";
        } catch (NullPointerException e) {
            return "error";
        }
    }

    @DeleteMapping("/cart")
    public String memberCartDelete(@RequestParam("cartNo") Long cartNo, Authentication authentication) {

        try{
            cartService.memberCartDelete(cartNo,authentication);
            return "redirect:/cart";
        } catch (NullPointerException e) {
            return "error";
        }

    }

}
