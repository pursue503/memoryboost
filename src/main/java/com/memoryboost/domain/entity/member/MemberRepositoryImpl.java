package com.memoryboost.domain.entity.member;

import com.memoryboost.domain.entity.cart.Cart;
import com.memoryboost.domain.entity.cart.QCart;
import com.memoryboost.domain.entity.email.MemberEmail;
import com.memoryboost.domain.entity.email.QMemberEmail;
import com.memoryboost.domain.entity.order.*;
import com.memoryboost.domain.entity.payment.bank.NoPassbook;
import com.memoryboost.domain.entity.payment.bank.QNoPassbook;
import com.memoryboost.domain.entity.payment.kakao.KaKaoPayment;
import com.memoryboost.domain.entity.payment.kakao.QKaKaoPayment;
import com.memoryboost.domain.entity.post.*;
import com.memoryboost.domain.entity.product.review.ProductReview;
import com.memoryboost.domain.entity.product.review.QProductReview;
import com.memoryboost.domain.entity.refund.QRefund;
import com.memoryboost.domain.entity.refund.Refund;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

//    @Override
//    public List<Member> findByMemberEmail(String memberEmail) {
//        QMember member = QMember.member;
//        return queryFactory.selectFrom(member)
//                .where(member.memberEmail.eq(memberEmail))
//                .fetch();
//    }


    @Override
    public List<Member> findMemberLoginId(String memberEmail, String memberSns) {
        QMember member = QMember.member;
        return queryFactory.selectFrom(member)
                .where(member.memberEmail.eq(memberEmail).and(member.memberSns.eq(memberSns)))
                .fetch();
    }

    @Override
    public List<Post> findByMemberPostAll(Member member) {
        QPost post = QPost.post;
        return queryFactory.selectFrom(post).where(post.member.eq(member)).fetch();
    }

    @Override
    public List<PostImage> findByMemberPostImageAll(Post post) {
        QPostImage postImage = QPostImage.postImage;
        return queryFactory.selectFrom(postImage).where(postImage.post.eq(post)).fetch();
    }

    @Override
    public List<PostReply> findByMemberPostReplyAll(Post post, Member member) {
        QPostReply postReply = QPostReply.postReply;
        return queryFactory.selectFrom(postReply).where(postReply.post.eq(post).or(postReply.member.eq(member))).fetch();
    }

    @Override
    public List<PostReply> findByMemberPostReplyOnly(Member member) {
        QPostReply postReply = QPostReply.postReply;
        return queryFactory.selectFrom(postReply).where(postReply.member.eq(member)).fetch();
    }

    @Override
    public List<Order> findByMemberOrderAll(Member member) {
        QOrder order = QOrder.order;
        return queryFactory.selectFrom(order).where(order.member.eq(member)).fetch();
    }

    @Override
    public List<Refund> findByMemberRefundAll(Order order) {
        QRefund refund = QRefund.refund;
        return queryFactory.selectFrom(refund).where(refund.order.eq(order)).fetch();
    }

    @Override
    public List<OrderList> findByMemberOrderListAll(Order order) {
        QOrderList orderList = QOrderList.orderList;
        return queryFactory.selectFrom(orderList).where(orderList.order.eq(order)).fetch();
    }

    @Override
    public NoPassbook findByMemberNoPassBook(Order order) {
        QNoPassbook noPassbook = QNoPassbook.noPassbook;
        return queryFactory.selectFrom(noPassbook).where(noPassbook.order.eq(order)).fetchOne();
    }

    @Override
    public DeliveryInformation findByMemberOrderDeliveryInformation(Order order) {
        QDeliveryInformation deliveryInformation = QDeliveryInformation.deliveryInformation;
        return queryFactory.selectFrom(deliveryInformation).where(deliveryInformation.order.eq(order)).fetchOne();
    }

    @Override
    public KaKaoPayment findByMemberOrderKaKaoPayment(Order order) {
        QKaKaoPayment kaKaoPayment = QKaKaoPayment.kaKaoPayment;
        return queryFactory.selectFrom(kaKaoPayment).where(kaKaoPayment.order.eq(order)).fetchOne();
    }

    @Override
    public List<Cart> findByMemberCartAll(Member member) {
        QCart cart = QCart.cart;
        return queryFactory.selectFrom(cart).where(cart.member.eq(member)).fetch();
    }

    @Override
    public List<MemberEmail> findByMemberAuthCodeAll(Member member) {
        QMemberEmail memberEmail = QMemberEmail.memberEmail;
        return queryFactory.selectFrom(memberEmail).where(memberEmail.memberId.eq(member)).fetch();
    }

    @Override
    public List<ProductReview> findByMemberProductReviewAll(Member member) {
        QProductReview productReview = QProductReview.productReview;
        return queryFactory.selectFrom(productReview).where(productReview.memberId.eq(member)).fetch();
    }
}
