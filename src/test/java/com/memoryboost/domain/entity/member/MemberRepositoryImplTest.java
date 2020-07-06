package com.memoryboost.domain.entity.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MemberRepositoryImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void querydslCustomTest(){
        List<Member> memberList = memberRepository.findByMemberEmail("abc1234@naber.com");

        if(memberList.isEmpty()) {
            log.info("비었습니다.");
        } else {
            memberList.forEach(member -> log.info(member.getMemberEmail()));
        }
    }

}
