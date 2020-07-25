package com.memoryboost.domain.entity.order;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.vo.order.response.MemberOrderResponseVO;
import com.memoryboost.domain.vo.order.response.OrderPaymentResponseVO;
import com.memoryboost.domain.vo.orderdetail.OrderDetailProductResponseVO;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderPaymentResponseVO> setOrderPayment(List<Long> cartList);

    List<MemberOrderResponseVO> findByMemberOrder(Member member);

    //주문 한거 상세 보기

    //상품 정보들
    List<OrderDetailProductResponseVO> findByOrderDetailProduct(Long orderNo, Member member);

}
