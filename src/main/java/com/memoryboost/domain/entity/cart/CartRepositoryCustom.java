package com.memoryboost.domain.entity.cart;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.vo.cart.response.MemberCartResponseVO;

import java.util.List;

public interface CartRepositoryCustom {

    List<MemberCartResponseVO> findByMemberCart(Member memberEntity);

    Cart findByCartNoAndMemberId(Long cartNo, Member member);

}
