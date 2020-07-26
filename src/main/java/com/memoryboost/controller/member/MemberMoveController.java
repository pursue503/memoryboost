package com.memoryboost.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MemberMoveController {

    //회원의 페이지 이동을 처리하는 컨트롤러

    //로그인 페이지로 이동.
    @GetMapping("/members/signin")
    public String signinPage(){
        return "member/signin";
    }

    //계정찾기 페이지로 이동.
    @GetMapping("/members/lost-account")
    public String lostAccount() { return "member/lost_account"; }

    //회원가입 페이지로 이동.
    @GetMapping("/members/signup")
    public String signupPage() { return "member/signup"; }

    //sns회원가입 페이지로 이동.
    @GetMapping("/members/sns-signup")
    public String snsSignupPage() { return "member/sns_signup"; }

    //마이페이지 이동
    @GetMapping("/members/mypage")
    public String myInfo(){
        return "mypage/myinfo";
    }

    //SNS 로그인
    @GetMapping("/member/signin/google")
    public String forGoogle(){
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/member/signin/naver")
    public String forNaver(){
        return "redirect:/oauth2/authorization/naver";
    }

    @GetMapping("/member/signin/kakao")
    public String forKakao(){
        return "redirect:/oauth2/authorization/kakao";
    }

}
