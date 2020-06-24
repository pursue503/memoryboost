package com.memoryboost.domain.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    //로그인관련
    //자체서비스
    //회원로그인 회원정보가 존재하는지 찾기
    Member findByMemberLoginIdAndMemberSns(String memberLoginId, String memberSns);

    //SNS
    //sns 로그인 , 회원정보가 존재하는지 찾기
    //찾을 entity , findByMemberEmail member entity 의 memberEmail -> SELECT * FROM member WHERE memberEmail
    Optional<Member> findByMemberEmailAndMemberSns(String memberEmail, String memberSns);
    
    //회원가입

    //아이디 중복체크
    int countByMemberLoginId(String memberLoginId);

}
