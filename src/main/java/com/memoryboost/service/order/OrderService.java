package com.memoryboost.service.order;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.order.OrderRepository;
import com.memoryboost.domain.vo.kakao.KaKaoPayApprovalVO;
import com.memoryboost.domain.vo.member.MemberCustomVO;
import com.memoryboost.domain.vo.order.response.MemberOrderResponseVO;
import com.memoryboost.domain.vo.order.response.OrderPaymentResponseVO;
import com.memoryboost.util.kakao.KaKaoPay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final KaKaoPay kaKaoPay;


    @Transactional(readOnly = true)
    public List<OrderPaymentResponseVO> orderPaymentReady(List<Long> cartList){
        return orderRepository.setOrderPayment(cartList);
    }

    public int orderTotalAmount(List<OrderPaymentResponseVO> paymentResponseVOList) {

        int totalAmount = 2500;
        for(OrderPaymentResponseVO order : paymentResponseVOList) {
            totalAmount += order.getProductCnt() * order.getProductPrice();
        }
        return totalAmount;
    }

    @Transactional(readOnly = true)
    public List<MemberOrderResponseVO> memberOrderResponseVOList(Authentication authentication) {
        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);
        return orderRepository.findByMemberOrder(member);
    }


    public String kaKaoPayReady(Authentication authentication, String totalAmout) {
        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);


        return kaKaoPay.kaKaoPayReady(member, "장바구니", totalAmout);
    }

    public KaKaoPayApprovalVO kaKaoPayApprovalVO(String paToken , Authentication authentication) {
        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);

        return kaKaoPay.kaKaoPayApproval(paToken, member);
    }

}
