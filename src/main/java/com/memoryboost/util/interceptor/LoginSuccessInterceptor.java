package com.memoryboost.util.interceptor;

import com.memoryboost.domain.vo.member.MemberCustomVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginSuccessInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //로그인 회원의 권한 검사.
        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            MemberCustomVO memberCustomVO = (MemberCustomVO) principal;

            if(memberCustomVO != null) {
                if(memberCustomVO.isMemberSt()) {
                    return true;
                } else {
                    //사용 권한이 존재하지 않음
                    response.sendRedirect("/members/logout");
                    return false;
                }
            } else {
                return true;
            }

        } catch (ClassCastException  | NullPointerException e) {
            return true;
        }






    }
}
