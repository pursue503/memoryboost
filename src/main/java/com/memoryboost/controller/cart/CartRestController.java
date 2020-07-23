package com.memoryboost.controller.cart;

import com.memoryboost.domain.dto.cart.request.CartSaveRequestDTO;
import com.memoryboost.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;

    //상품넣기
    @PostMapping("/cart")
    public Map<String,Boolean> cartSave(@RequestBody List<CartSaveRequestDTO> cartSaveRequestDTO , Authentication authentication) {
        Map<String, Boolean> resultMap = new HashMap<>();
        try {
            resultMap.put("result", cartService.cartSave(cartSaveRequestDTO, authentication));
            return resultMap;
        } catch (NullPointerException e) {
            resultMap.put("result", false);
            return resultMap;
        }
    }

}