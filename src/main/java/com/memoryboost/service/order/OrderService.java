package com.memoryboost.service.order;

import com.memoryboost.domain.entity.order.OrderRepository;
import com.memoryboost.domain.vo.order.response.OrderPaymentResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<OrderPaymentResponseVO> orderPaymentReady(List<Long> cartList){
        return orderRepository.setOrderPayment(cartList);
    }

}
