package com.memoryboost.domain.entity.member;

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
}
