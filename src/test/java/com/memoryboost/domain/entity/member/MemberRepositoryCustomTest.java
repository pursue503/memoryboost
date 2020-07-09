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
public class MemberRepositoryCustomTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findMemberLoginId(){

        List<Member> memberList = memberRepository.findMemberLoginId("pursue503@naver.com","memoryboost");

        memberList.forEach(member -> log.info(member.getMemberLoginId()));

    }

}
