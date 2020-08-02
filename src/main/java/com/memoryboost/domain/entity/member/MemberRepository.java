package com.memoryboost.domain.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>,MemberRepositoryCustom {

    //자체서비스
    //회원로그인 회원정보가 존재하는지 찾기
    Member findByMemberLoginIdAndMemberSns(String memberLoginId, String memberSns);

    //비밀번호찾기
    //회원아이디 유효한지 확인
    int countByMemberLoginIdAndMemberEmail(String memberLoginId, String memberEmail);

    //회원가입
    //아이디 중복체크
    int countByMemberLoginId(String memberLoginId);

    //SNS
    //sns 로그인 , 회원정보가 존재하는지 찾기 , + 자사서비스 아이디찾기에 사용.
    //찾을 entity , findByMemberEmail member entity 의 memberEmail -> SELECT * FROM member WHERE memberEmail
    Optional<Member> findByMemberSnsIdAndMemberSns(String memberSnsId, String memberSns);

}
