package com.memoryboost.service.member;

import com.memoryboost.domain.dto.member.memoryboost.request.MemberSaveRequestDTO;
import com.memoryboost.domain.dto.member.memoryboost.request.MemberUpdateRequestDTO;
import com.memoryboost.domain.dto.member.sns.MemberSNSInfoUpdateRequestDTO;
import com.memoryboost.domain.entity.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
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

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void createMember(){

        MemberSaveRequestDTO saveRequestDTO = new MemberSaveRequestDTO();
        saveRequestDTO.setMemberLoginId("abc1234");
        saveRequestDTO.setMemberPw("aaa1111");
        saveRequestDTO.setMemberEmail("abc1234@naver.com");
        saveRequestDTO.setMemberTel("010-1111-1111");
        saveRequestDTO.setMemberName("홍길동");
        saveRequestDTO.setMemberZipCode("00000");
        saveRequestDTO.setMemberAddress("서울특별시");
        saveRequestDTO.setMemberDetailAddress("종로구");
        memberRepository.save(saveRequestDTO.toEntity());
    }

    @After
    public void deleteMember(){
        memberRepository.deleteAll();
    }


    @Test
    public void loginIdOverlapCheck(){
        boolean result = memberService.loginIdOverlapCheck("abc1234");
        log.info("결과: " + result);
    }

    @Test
    public void memberUpdate(){

        MemberUpdateRequestDTO updateRequestDTO = new MemberUpdateRequestDTO();
        updateRequestDTO.setMemberPw("aaa1111");
        updateRequestDTO.setMemberTel("010-1111-1111");

        boolean flag = memberService.memberUpdate(1L,updateRequestDTO);
        
        if(flag) {
            log.info("업데이트완료");
        } else {
            log.info("업데이트 실패");
        }

    }


}
