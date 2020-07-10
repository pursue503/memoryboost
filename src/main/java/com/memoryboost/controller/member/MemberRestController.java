package com.memoryboost.controller.member;

import com.memoryboost.domain.dto.member.memoryboost.response.MemberFindByLoginIdResponseDTO;
import com.memoryboost.domain.vo.member.MemberOAuth2VO;
import com.memoryboost.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/members/findid/{memberEmail}")
    public List<MemberFindByLoginIdResponseDTO> findByMemberLoginId(@PathVariable("memberEmail") String memberEmail) {

        return memberService.memberFindByLoginId(memberEmail);
    }

    //회원인증번호 정송
    @GetMapping("/members/findpw/{memberLoginId}/{memberEmail}")
    public Map<String,Boolean> pwAuthCodeSend(@PathVariable("memberLoginId") String memberLoginId, @PathVariable("memberEmail") String memberEmail){
        Map<String,Boolean> resultMap = new HashMap<>();
        if(memberService.memberExistenceCheck(memberLoginId,memberEmail)) {
            if(memberService.memberFindByPwAuthCodeSend(memberLoginId,memberEmail)) {
                resultMap.put("result", true);
                return resultMap;
            }
        }
        resultMap.put("result", false);
        return resultMap;
    }

    //회원정보수정
    @PutMapping("/members/pw/{memberLoginId}/{emailCode}")
    public Map<String,Boolean> findByPw(@PathVariable("memberLoginId") String memberLoginId, @PathVariable("emailCode") String emailCode ){
        Map<String,Boolean> resultMap = new HashMap<>();
        resultMap.put("result", memberService.findByMemberPw(memberLoginId,emailCode));
        return resultMap;
    }

    @PostMapping("/members/mypage/pw-check/{memberId}")
    public Map<String, Boolean> mypagePasswordConfirm(@PathVariable("memberId") Long memberId, @RequestParam("memberPw") String memberPw) {
        Map<String, Boolean> resultMap = new HashMap<>();
        try {
            resultMap.put("result", memberService.mypagePasswordConfirm(memberId,memberPw));
            return resultMap;
        }catch (NullPointerException e) {
            resultMap.put("result", false);
            return resultMap;
        }
    }

}
