package com.memoryboost.domain.entity.member;

import java.util.List;

public interface MemberRepositoryCustom {
    
    //memoryboost 회원 아이디를 찾음
    List<Member> findMemberLoginId(String memberEmail, String memberSns);



}
