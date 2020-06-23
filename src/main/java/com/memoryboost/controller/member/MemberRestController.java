package com.memoryboost.controller.member;

import com.memoryboost.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class MemberRestController {

    //Json 타입등이 필요하거나 비동기로 처리해야할때 사용 하는 컨트롤러

    @Autowired
    private MemberService memberService;

    //회원아이디 중복체크
    @GetMapping("/members/{memberLoginId}/overlap/check")
    public Map<String,Boolean> loginIdOverlapCheck(@PathVariable("memberLoginId") String memberloginId){
        Map<String,Boolean> result = new HashMap<>();
        result.put("result",memberService.loginIdOverlapCheck(memberloginId));
        return result;

    }

}
