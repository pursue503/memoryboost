package com.memoryboost.controller.member;

import com.memoryboost.domain.dto.member.memoryboost.request.MemberUpdateRequestDTO;
import com.memoryboost.domain.dto.member.sns.MemberSNSInfoUpdateRequestDTO;
import com.memoryboost.domain.dto.member.memoryboost.request.MemberSaveRequestDTO;
import com.memoryboost.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberCRUDController {

    //회원의 CRUD등을 관리하는 컨트롤러


    private final MemberService memberService;

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

    //sns 계정 정보 업데이트
    @PutMapping("/members/sns")
    @ResponseBody
    public Boolean snsMemberInfoUpdate(Authentication authentication, MemberSNSInfoUpdateRequestDTO updateRequestDTO){
        try{
            return memberService.snsMemberInfoUpdate(authentication,updateRequestDTO);
        }catch (IllegalArgumentException e) {
            return false;
        }
    }

    //회원 정보 업데이트
    @PutMapping("/members")
    @ResponseBody
    public Boolean memberUpdate(Authentication authentication, MemberUpdateRequestDTO updateRequestDTO){
        log.info("업데이트");
        try{
            if(memberService.memberUpdate(authentication,updateRequestDTO)) {
                return true;
            } else {
                return false; // 비밀번호 정규식 매칭안댐
            }
        } catch (NullPointerException e) {
            return false; // 회원아이디가 존재하지 않음.
        }
    }

    @DeleteMapping("/members")
    @ResponseBody
    public Map<String , Boolean> memberLeave(Authentication authentication) {

        Map<String , Boolean> resultMap = new HashMap<>();
        try{
            resultMap.put("result",memberService.memberLeave(authentication));
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", false);
        }

        return resultMap;
    }


}
