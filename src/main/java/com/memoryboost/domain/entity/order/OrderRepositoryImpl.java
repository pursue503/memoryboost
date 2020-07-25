package com.memoryboost.domain.entity.order;

import com.memoryboost.domain.entity.cart.QCart;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.product.QProduct;
import com.memoryboost.domain.vo.order.response.MemberOrderResponseVO;
import com.memoryboost.domain.vo.order.response.OrderPaymentResponseVO;
import com.memoryboost.domain.vo.orderdetail.OrderDetailProductResponseVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderPaymentResponseVO> setOrderPayment(List<Long> cartList) {

        QCart cart = QCart.cart;
        QProduct product = QProduct.product;

        JPAQuery<OrderPaymentResponseVO> jpaQuery = queryFactory.select(Projections.fields(OrderPaymentResponseVO.class,
                cart.cartNo,cart.productCnt,product.productNo,product.productThumbnail,product.productName,product.productPrice))
                .from(cart).leftJoin(product).on(cart.product.eq(product));

        BooleanBuilder builder = new BooleanBuilder();

        for(Long cartNo : cartList) {
            builder.or(cart.cartNo.eq(cartNo));
        }

        jpaQuery.where(builder).orderBy(cart.cartNo.asc());

        return jpaQuery.fetch();
    }

    @Override
    public List<MemberOrderResponseVO> findByMemberOrder(Member member) {
        QOrder order = QOrder.order;
        return queryFactory.select(Projections.fields(MemberOrderResponseVO.class,
                order.orderNo,order.orderDate,order.orderSt,order.orderTrackingNumber,order.orderTotalAmount))
                .from(order).where(order.member.eq(member)).fetch();
    }

    @Override
    public List<OrderDetailProductResponseVO> findByOrderDetailProduct(Long orderNo, Member member) {
        QProduct product = QProduct.product;
        QOrder order = QOrder.order;
        QOrderList orderList = QOrderList.orderList;
        return null;
//        return queryFactory.select(Projections.fields(OrderDetailProductResponseVO.class,
//                ))
    }
}
