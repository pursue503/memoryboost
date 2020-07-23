package com.memoryboost.domain.entity.cart;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.product.QProduct;
import com.memoryboost.domain.vo.cart.response.MemberCartResponseVO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberCartResponseVO> findByMemberCart(Member memberEntity) {
        QCart cart = QCart.cart;
        QProduct product = QProduct.product;

        return queryFactory.select(Projections.fields(MemberCartResponseVO.class,
                cart.cartNo,cart.productCnt,product.productNo,product.productThumbnail,product.productDescription))
                .from(cart).leftJoin(product).on(cart.product.eq(product))
                .where(cart.member.eq(memberEntity)).fetch();
    }

    @Override
    public Cart findByCartNoAndMemberId(Long cartNo, Member member) {
        QCart cart = QCart.cart;

        return queryFactory.selectFrom(cart).where(cart.cartNo.eq(cartNo).and(cart.member.eq(member))).fetchOne();
    }
}
