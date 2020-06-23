package com.memoryboost.domain.member;

import com.memoryboost.domain.entity.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void loginIdOverlapCheck(){
        log.info("count 값: " + memberRepository.countByMemberLoginId("abc1234"));
    }

}
