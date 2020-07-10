package com.memoryboost.service.member;

import com.memoryboost.domain.dto.member.memoryboost.request.MemberUpdateRequestDTO;
import com.memoryboost.domain.dto.member.sns.MemberSNSInfoUpdateRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Member;


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

    //sns 계정 업데이트

    @Test
    public void snsMemberInfoUpdate(){

        MemberSNSInfoUpdateRequestDTO updateRequestDTO = MemberSNSInfoUpdateRequestDTO.builder().
                memberTel("010-98923984").
                memberZipCode("30203").
                memberAddress("서울특별시 서울서울").
                memberDetailAddress("단성사").
                build();
    }

    @Test
    public void memberUpdate(){

        MemberUpdateRequestDTO updateRequestDTO = new MemberUpdateRequestDTO();
        updateRequestDTO.setMemberPw("aaa1111");
        updateRequestDTO.setMemberTel("010-1111-1111");

        boolean flag = memberService.memberUpdate(32L,updateRequestDTO);
        
        if(flag) {
            log.info("업데이트완료");
        } else {
            log.info("업데이트 실패");
        }

    }


}
