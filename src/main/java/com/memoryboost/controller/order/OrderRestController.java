package com.memoryboost.controller.order;

import com.memoryboost.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping("/kakaopay-ready")
    public String kaKaoPayReady(@RequestParam("totalAmount") String totalAmount, Authentication authentication){
        return orderService.kaKaoPayReady(authentication,totalAmount);
    }

}