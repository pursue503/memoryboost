package com.memoryboost.config.security.custom;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.vo.member.MemberCustomVO;
import com.memoryboost.domain.vo.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MemoryBoostSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        //자체 서비스 로그인 핸들러
//        MemberVO memberVO = (MemberVO) authentication.getPrincipal();
        if(!memberCustomVO.isMemberSt()) { // true 면 사용가능 false 사용불가능 false ! 반전으로 true if 문을 실행
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('이메일 인증이 진행되지 않은 아이디입니다.\\n이메일 인증을 진행해주세요');");
            out.println("location.href='/members/logout'");
            out.println("</script>");
            out.flush();
            return;
        } else {
            //아닐경우 로그인 통과 그대로 들어가려했던 url 로 이동.
            super.onAuthenticationSuccess(request, response, authentication);
        }


    }

}
