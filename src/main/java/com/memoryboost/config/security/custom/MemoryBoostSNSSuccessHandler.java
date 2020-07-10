package com.memoryboost.config.security.custom;

import com.memoryboost.domain.vo.member.MemberOAuth2VO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MemoryBoostSNSSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        MemberOAuth2VO memberOAuth2VO = (MemberOAuth2VO) authentication.getPrincipal();

        if(!memberOAuth2VO.isMemberSt()) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("if(confirm('SNS로그인은 1회 개인정보를 입력해주셔야 합니다\\n지금 이동하시겠습니까?')) {");
            out.println("location.href='/members/sns-signup'}");
            out.println("else {");
            out.println("location.href='/members/logout'");
            out.println("}");
            out.println("</script>");
            out.flush();
            return;
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }


    }
}
