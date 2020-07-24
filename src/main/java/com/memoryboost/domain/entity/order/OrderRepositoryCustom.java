package com.memoryboost.domain.entity.order;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.vo.order.response.MemberOrderResponseVO;
import com.memoryboost.domain.vo.order.response.OrderPaymentResponseVO;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderPaymentResponseVO> setOrderPayment(List<Long> cartList);

    List<MemberOrderResponseVO> findByMemberOrder(Member member);

}
