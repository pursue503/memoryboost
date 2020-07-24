package com.memoryboost.controller.order;

import com.memoryboost.domain.vo.order.response.OrderPaymentResponseVO;
import com.memoryboost.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderMoveController {

    private final OrderService orderService;

    //주문
    @GetMapping("/order")
    public String order(@RequestParam("cartList") ArrayList<Long> cartList, Model model){

        List<OrderPaymentResponseVO> orderPaymentResponseVOList = orderService.orderPaymentReady(cartList);
        model.addAttribute("order", orderPaymentResponseVOList);
        model.addAttribute("totalAmount", orderService.orderTotalAmount(orderPaymentResponseVOList));
        return "주소";
    }

    @GetMapping("/mypage-orderList")
    public String myPageOrderList(Authentication authentication , Model model) {
        model.addAttribute("order", orderService.memberOrderResponseVOList(authentication));
        return "주소";
    }

    @GetMapping("/kakaopay-success")
    public String kakaoSuccess(@RequestParam("pg_token") String pgToken , Authentication authentication) {
        log.info("들어옴!");
        log.info(orderService.kaKaoPayApprovalVO(pgToken,authentication).toString());
        return "여기에 이벤트 발생시킬 url 써주세요";
    }

    @GetMapping("/order-complete")
    public String orderComplete(){
        return null;
    }

}
