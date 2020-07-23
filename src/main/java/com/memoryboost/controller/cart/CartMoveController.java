package com.memoryboost.controller.cart;

import com.memoryboost.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CartMoveController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String memberCartList(Authentication authentication , Model model){

        model.addAttribute("cartList", cartService.memberCart(authentication));
        return "cart 페이지";
    }

}
