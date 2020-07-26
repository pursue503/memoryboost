package com.memoryboost.domain.entity.order;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.vo.order.response.MemberOrderResponseVO;
import com.memoryboost.domain.vo.order.response.OrderPaymentResponseVO;
import com.memoryboost.domain.vo.orderdetail.OrderDetailDeliveryInfoResponseVO;
import com.memoryboost.domain.vo.orderdetail.OrderDetailPaymentInfoResponseVO;
import com.memoryboost.domain.vo.orderdetail.OrderDetailProductResponseVO;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderPaymentResponseVO> setOrderPayment(List<Long> cartList);

    //회원의 주문 내역
    List<MemberOrderResponseVO> findByMemberOrder(Member member,int page);

    // 회원의 주문 내역 전체 개수
    int findByMemberOrderPaging(Member member);


    //주문 한거 상세 보기

    //상품 정보들
    List<OrderDetailProductResponseVO> findByOrderDetailProduct(Long orderNo, Member member);

    //결제 정보
    OrderDetailPaymentInfoResponseVO findByOrderDetailPaymentInfo(Long orderNo, Member member);

    //배송 정보
    OrderDetailDeliveryInfoResponseVO findByOrderDetailDeliveryInfo(Long orderNo, Member member);

    //주문 상세 정보에 대한 주문 상태들,,
    //재활용
    MemberOrderResponseVO findByOrderDetail(Long orderNo , Member member);


    //배송 정보 업데이트

    //주문 정보 가져오기 회원정보로 같이 조회
    Order findByOrder(Long orderNo, Member member);

    //배송지 정보 업데이트에 사용할 객체찾기
    DeliveryInformation findByDeliveryInformation(Order order);

}
