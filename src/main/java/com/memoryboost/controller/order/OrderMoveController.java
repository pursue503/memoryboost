package com.memoryboost.controller.order;

import com.memoryboost.domain.dto.order.request.OrderSaveRequestDTO;
import com.memoryboost.domain.dto.order.request.OrderSingleProductSaveRequestDTO;
import com.memoryboost.domain.vo.order.response.OrderPaymentResponseVO;
import com.memoryboost.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "/order";
    }

    //단품 주문
    @GetMapping("/order/single-product")
    public String singleOrder(@RequestParam("productNo") Long productNo, @RequestParam("productCnt") int productCnt, Model model) {
        List<OrderPaymentResponseVO> orderPaymentResponseVOList = orderService.singleProduct(productNo,productCnt);
        model.addAttribute("order",orderPaymentResponseVOList);
        model.addAttribute("totalAmount", orderService.orderTotalAmount(orderPaymentResponseVOList));
        return "/order";
    }

    @GetMapping("/mypage-orderList")
    public String myPageOrderList(Authentication authentication , Model model) {
        model.addAttribute("order", orderService.memberOrderResponseVOList(authentication));
        return "mypage/orderlist";
    }

    @GetMapping("/kakaopay-success")
    public String kakaoSuccess(@RequestParam("pg_token") String pgToken , Authentication authentication,Model model) {
        log.info("들어옴!");
        model.addAttribute("tid", orderService.kaKaoPayApprovalVO(pgToken,authentication).getTid());
        return "여기에 이벤트 발생시킬 url 써주세요";
    }

    @PostMapping("/order-complete")
    public String orderComplete(@RequestParam(value = "cartNo" , required = false) ArrayList<Long> cartList , OrderSaveRequestDTO orderSaveRequestDTO , Authentication authentication,
                                OrderSingleProductSaveRequestDTO singleProductSaveRequestDTO, @RequestParam(value = "tid",required = false) String tid){
        try{
            log.info(singleProductSaveRequestDTO.toString());
            if(orderService.orderSave(authentication,cartList,orderSaveRequestDTO,singleProductSaveRequestDTO,tid)) {
                return "redirect:/";
            } else {
                return "error";
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            return "error";
        }
    }

//    //회원의 주문내역
//    @GetMapping("/order/detail")
//    public String orderDetail(@RequestParam("orderNo") Long orderNo , Authentication authentication) {
//
//    }

}