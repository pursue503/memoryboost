package com.memoryboost.domain.entity.member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findByMemberEmail(String memberEmail);
}
