package com.memoryboost.domain.entity.member;

import com.memoryboost.domain.entity.cart.Cart;
import com.memoryboost.domain.entity.email.MemberEmail;
import com.memoryboost.domain.entity.order.DeliveryInformation;
import com.memoryboost.domain.entity.order.Order;
import com.memoryboost.domain.entity.order.OrderList;
import com.memoryboost.domain.entity.payment.bank.NoPassbook;
import com.memoryboost.domain.entity.payment.kakao.KaKaoPayment;
import com.memoryboost.domain.entity.post.Post;
import com.memoryboost.domain.entity.post.PostImage;
import com.memoryboost.domain.entity.post.PostReply;
import com.memoryboost.domain.entity.product.review.ProductReview;
import com.memoryboost.domain.entity.refund.Refund;

import java.util.List;

public interface MemberRepositoryCustom {
    
    //memoryboost 회원 아이디를 찾음
    List<Member> findMemberLoginId(String memberEmail, String memberSns);

    //회원 탈퇴 모든걸 여기서 조회 해서 들고옴

    //게시판
    List<Post> findByMemberPostAll(Member member);
    List<PostImage> findByMemberPostImageAll(Post post);
    List<PostReply> findByMemberPostReplyAll(Post post , Member member);

    //댓글만 따로 삭제 글을 안썼을경우
    List<PostReply> findByMemberPostReplyOnly(Member member);

    //결제
    List<Order> findByMemberOrderAll(Member member);
    List<Refund> findByMemberRefundAll(Order order);
    List<OrderList> findByMemberOrderListAll(Order order);
    NoPassbook findByMemberNoPassBook(Order order);
    DeliveryInformation findByMemberOrderDeliveryInformation(Order order);
    KaKaoPayment findByMemberOrderKaKaoPayment(Order order);

    //장바구니
    List<Cart> findByMemberCartAll(Member member);

    //리뷰삭제
    List<ProductReview> findByMemberProductReviewAll(Member member);

    //마지막으로 회원쪽
    List<MemberEmail> findByMemberAuthCodeAll(Member member);

}
