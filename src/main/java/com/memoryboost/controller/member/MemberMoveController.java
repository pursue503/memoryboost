package com.memoryboost.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MemberMoveController {

    //회원의 페이지 이동을 처리하는 컨트롤러

    //회원가입 페이지로 이동.
    @GetMapping("/members/signin")
    public String signinPage(){
        return "login";
    }

    //마이페이지 이동
    @GetMapping("/members/mypage")
    public String myInfo(){
        return "memberInfo";
    }

}
