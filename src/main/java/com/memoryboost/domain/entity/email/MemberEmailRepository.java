package com.memoryboost.domain.entity.email;

import com.memoryboost.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberEmailRepository extends JpaRepository<MemberEmail,Long> {

    int countByEmailNoAndMemberIdAndEmailCode(Long memberNo, Member member, String emailCode);

    int countByMemberIdAndEmailCode(Member member, String emailCode);

}
