package com.memoryboost.controller.order;

import com.memoryboost.domain.dto.estimate.request.EstimateRequestDTO;
import com.memoryboost.domain.dto.order.request.OrderInfoUpdateDTO;
import com.memoryboost.domain.dto.order.request.OrderSaveRequestDTO;
import com.memoryboost.domain.vo.order.response.OrderPaymentResponseVO;
import com.memoryboost.service.order.OrderService;
import com.memoryboost.service.paging.PagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderMoveController {

    private final OrderService orderService;
    private final PagingService pagingService;
    //주문
    @GetMapping("/order")
    public String order(@RequestParam("cartList") ArrayList<Long> cartList, Model model){

        List<OrderPaymentResponseVO> orderPaymentResponseVOList = orderService.orderPaymentReady(cartList);
        model.addAttribute("order", orderPaymentResponseVOList);
        model.addAttribute("totalAmount", orderService.orderTotalAmount(orderPaymentResponseVOList));
        return "order";
    }

    //단품 주문
    @GetMapping("/order/single-product")
    public String singleOrder(@RequestParam("productNo") Long productNo, @RequestParam("productCnt") int productCnt, Model model , HttpSession session) {
        List<OrderPaymentResponseVO> orderPaymentResponseVOList = orderService.singleProduct(productNo,productCnt);
        int totalAmount = orderService.orderTotalAmount(orderPaymentResponseVOList);

        model.addAttribute("order",orderPaymentResponseVOList);
        model.addAttribute("totalAmount", totalAmount);
        session.setAttribute("order",orderPaymentResponseVOList);
        session.setAttribute("totalAmount",totalAmount);

        return "order";
    }

    //시큐리티에서 무조건 가능하게해야함
    @PostMapping("/order/estimate")
    @ResponseBody
    public Boolean estimateOrder(@RequestBody List<EstimateRequestDTO> estimateRequestDTOList, HttpSession session) {

        log.info(estimateRequestDTOList.toString());
        List<OrderPaymentResponseVO> orderPaymentResponseVOList = orderService.estimateOrder(estimateRequestDTOList);
        session.setAttribute("order", orderPaymentResponseVOList);
        return true;
    }

    @GetMapping("/order/estimate")
    public String estimateOrder(HttpSession session, Model model) {

        List<OrderPaymentResponseVO> orderPaymentResponseVOList = (List<OrderPaymentResponseVO>) session.getAttribute("order");
        model.addAttribute("order",orderPaymentResponseVOList);
        model.addAttribute("totalAmount",orderService.orderTotalAmount(orderPaymentResponseVOList));
        return "order";
    }

    @GetMapping("/mypage-orderList")
    public String myPageOrderList(Authentication authentication , Model model , @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        model.addAttribute("order", orderService.memberOrderResponseVOList(authentication,page));
        model.addAttribute("paging", pagingService.memberOrderListPaging(authentication, page));
        return "mypage/orderlist";
    }

    @GetMapping("/kakaopay-success")
    public String kakaoSuccess(@RequestParam("pg_token") String pgToken , Authentication authentication,Model model) {
        model.addAttribute("tid", orderService.kaKaoPayApprovalVO(pgToken,authentication).getTid());
        return "payment/kakao-success";
    }

    @PostMapping("/order-complete")
    public String orderComplete(@RequestParam(value = "cartList" , required = false) ArrayList<Long> cartList , OrderSaveRequestDTO orderSaveRequestDTO , Authentication authentication,
                                @RequestParam(value = "tid",required = false) String tid , HttpSession session){
        try{
            if(orderService.orderSave(authentication,cartList,orderSaveRequestDTO,session,tid)) {
                return "redirect:/mypage-orderList";
            } else {
                return "error";
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            return "error";
        }
    }

    //회원의 주문내역
    @GetMapping("/order/detail")
    public String orderDetail(@RequestParam("orderNo") Long orderNo , Authentication authentication , Model model) {

        try{
            model.addAttribute("product", orderService.detailProductResponseVOList(orderNo,authentication));
            model.addAttribute("payment",orderService.detailPaymentInfoResponseVO(orderNo,authentication));
            model.addAttribute("delivery", orderService.detailDeliveryInfoResponseVO(orderNo,authentication));
            model.addAttribute("order",orderService.singleMemberOrderResponseVO(orderNo,authentication));
            return "mypage/orderdetail";
        } catch (NullPointerException e) {
            return "error";
        }
    }

    //주문 정보 업데이트
    @PutMapping("/order/info")
    public String orderInfoUpdate(OrderInfoUpdateDTO orderInfoUpdateDTO, Authentication authentication) {
        try{
            log.info("실행.");
            if(orderService.orderInfoUpdate(orderInfoUpdateDTO,authentication)) {
                return "redirect:/order/detail?orderNo=" + orderInfoUpdateDTO.getOrderNo();
            } else {
                return "error";
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "error";
        }

    }

    //리뷰 작성
    @GetMapping("/writereview")
    public String writeReviewPage() { return "mypage/writereview"; }
}
