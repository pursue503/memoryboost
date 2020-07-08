package com.memoryboost.controller.member;

import com.memoryboost.domain.dto.member.MemberSaveRequestDTO;
import com.memoryboost.domain.entity.email.MemberEmail;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.Role;
import com.memoryboost.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Controller
public class MemberCRUDController {

    //회원의 CRUD등을 관리하는 컨트롤러

    @Autowired
    private MemberService memberService;

    //회원가입
    @PostMapping("/members")
    public String memberSignUp(MemberSaveRequestDTO saveRequestDto){

        if (memberService.signUp(saveRequestDto)) return "redirect:/";
        else return "error";
    }

    //이메일 인증
    @GetMapping("/members/email/auth/check/{memberId}/{EmailNo}/{EmailCode}")
    public void memberEmailAuthCheck(@PathVariable("memberId") Long memberId, @PathVariable("EmailNo") Long emailNo,
                                     @PathVariable("EmailCode") String emailCode, HttpServletResponse response) throws IOException {

        if(memberService.memberEmailAuthCheck(memberId,emailNo,emailCode)) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<script>");
            out.println("alert('인증 되었습니다.');");
            out.println("location.href='/';");
            out.println("</script>");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<script>");
            out.println("alert('관리자에게 문의 해주세요..');");
            out.println("location.href='/';");
            out.println("</script>");
        }



    }

    //아이디찾기
    
    //비밀번호찾기   
    
}
