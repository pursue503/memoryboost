package com.memoryboost.service.member;

import com.memoryboost.util.email.MemoryBoostMailhandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;


    @Test
    public void loginIdOverlapCheck(){
        boolean result = memberService.loginIdOverlapCheck("abc1234");
        log.info("결과: " + result);
    }


}
