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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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

    //아이디찾기
    
    //비밀번호찾기   
    
}
