package com.memoryboost.service.order;

import com.memoryboost.domain.dto.order.request.OrderSaveRequestDTO;
import com.memoryboost.domain.dto.order.request.OrderSingleProductSaveRequestDTO;
import com.memoryboost.domain.entity.cart.Cart;
import com.memoryboost.domain.entity.cart.CartRepository;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.order.*;
import com.memoryboost.domain.entity.payment.bank.Bank;
import com.memoryboost.domain.entity.payment.bank.BankRepository;
import com.memoryboost.domain.entity.payment.bank.NoPassbook;
import com.memoryboost.domain.entity.payment.bank.NoPassbookRepository;
import com.memoryboost.domain.entity.payment.kakao.KaKaoPayment;
import com.memoryboost.domain.entity.payment.kakao.KaKaoPaymentRepository;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.vo.cart.response.MemberCartResponseVO;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final KaKaoPay kaKaoPay;
    private final CartRepository cartRepository;
    private final DeliveryInformationRepository deliveryInformationRepository;
    private final ProductRepository productRepository;
    private final OrderListRepository orderListRepository;
    private final NoPassbookRepository noPassbookRepository;
    private final BankRepository bankRepository;
    private final KaKaoPaymentRepository kaKaoPaymentRepository;

    @Transactional(readOnly = true)
    public List<OrderPaymentResponseVO> orderPaymentReady(List<Long> cartList){
        return orderRepository.setOrderPayment(cartList);
    }

    public int orderTotalAmount(List<OrderPaymentResponseVO> paymentResponseVOList) {

        int totalAmount = 0;
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

    @Transactional
    public boolean orderSave(Authentication authentication , ArrayList<Long> cartList , OrderSaveRequestDTO saveRequestDTO,
                                                OrderSingleProductSaveRequestDTO singleProductSaveRequestDTO, String tid) {
        //회원정보가져오기
        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);

        Order order = orderRepository.save(saveRequestDTO.orderEntity(member));


        //주문 상세 정보 입력.
        deliveryInformationRepository.save(saveRequestDTO.deliveryInformationEntity(order));
        if(order.getOrderPaymentGb() == 0) { //카카오 페이
            kaKaoPaymentRepository.save(KaKaoPayment.builder().order(order).tid(tid).build());
        } else { // 무통장
            Bank bank = bankRepository.findById(saveRequestDTO.getBankNo()).orElseThrow(NullPointerException::new);
            noPassbookRepository.save(NoPassbook.builder().bank(bank).order(order).build());
        }

        //장바 구니로 구매 한 건지 단일 제품 인지 확인.
        if(!cartList.isEmpty()) { // 장바 구니 구매
            log.info("비어있지않음..");
            //회원의 전체 장바 구니 목록을 가져옴.
            List<MemberCartResponseVO> memberCartList = cartRepository.findByMemberCart(member);
            for(MemberCartResponseVO memberCart : memberCartList) { // 회원 장바 구니와 같은것만 삭제.
                for(Long cartNo : cartList) {
                    if(cartNo == memberCart.getCartNo()) {
                        //장바 구니 에서 삭제 하고 order List 에 넣어 주는 작업
                        Cart cart = cartRepository.findById(cartNo).orElseThrow(NullPointerException::new);
                        cartRepository.delete(cart);
                        //상품 entity 찾기

                        orderListRepository.save(OrderList.builder().order(order).product(cart.getProduct()).productCnt(memberCart.getProductCnt()).build());
                    }
                }
            } // end MemberCartResponseVO


        } else { // 단일구매
            log.info("단일구매실행.");
            //cartNo 가 비어있을경우만 단품구매로 확인이됨
            Product product = productRepository.findById(singleProductSaveRequestDTO.getProductNo()).orElseThrow(NullPointerException::new);
            orderListRepository.save(OrderList.builder().order(order).product(product).productCnt(singleProductSaveRequestDTO.getProductCnt()).build());

        }
        return true;
    }

    //단일 제품 구매 - > 결제 페이지
    @Transactional(readOnly = true)
    public List<OrderPaymentResponseVO> singleProduct(Long productNo, int productCnt) {
        Product product = productRepository.findById(productNo).orElseThrow(NullPointerException::new);

        //1개만 넣어줌.단일제품
        List<OrderPaymentResponseVO> responseVOList = new ArrayList<>();
        OrderPaymentResponseVO orderPaymentResponseVO = OrderPaymentResponseVO.builder().productNo(product.getProductNo())
                .productName(product.getProductName()).productThumbnail(product.getProductThumbnail()).productPrice(product.getProductPrice())
                .productCnt(productCnt).build();
        responseVOList.add(orderPaymentResponseVO);
        return responseVOList;
    }

}
