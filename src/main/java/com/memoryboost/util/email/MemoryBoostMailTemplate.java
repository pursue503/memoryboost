package com.memoryboost.util.email;

import com.memoryboost.domain.entity.email.MemberEmail;
import lombok.Getter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

@Getter
@Component
public class MemoryBoostMailTemplate {

    //메일전송을 도와줄 클래스
    //회원의 인증코드를 생성시켜줄 랜덤메소드

    public String createRandomCode(int count){
        StringBuffer randomCode = new StringBuffer();
        Random rn = new Random();

        for(int i=0; i<count; i++) {
            int randomNumber = rn.nextInt(3);
            switch(randomNumber) {
                case 0:
                    //a-z
                    randomCode.append((char) ((int) (rn.nextInt(26)) + 97 ));
                    break;
                case 1 :
                    //A-Z
                    randomCode.append((char) ((int) (rn.nextInt(26)) + 65));
                    break;
                case 2:
                    //0-9
                    randomCode.append(rn.nextInt(10));
                    break;
            }
        }
        return randomCode.toString();
    }// end createRandomCode();

    //인증메일보내기
    public String signUpAuthMailTemplate(MemberEmail memberEmail){

        StringBuffer template = new StringBuffer();

        template.append("<h1> MemoryBoost 인증 메일입니다. </h1>");
        template.append("<h3> 인증을 진행하시려면 아래에 버튼을 눌러주세요. </h3>");
        template.append("<a href='");
        template.append("http://memoryboost/members/email/auth/check/"+memberEmail.getMemberId() + "/" + memberEmail.getEmailNo() + "/" + memberEmail.getEmailCode());
        template.append("'>");
        template.append("<button>인증</button>");
        template.append("</a>");
        return template.toString();
    }

    public String findByPwAuthCodeTemplate(MemberEmail memberEmail) {
        StringBuffer template = new StringBuffer();

        template.append("<h1> MemoryBoost 비밀번호 찾기 인증번호 입니다 </h1>");
        template.append("아래 인증코드를 입력해주세요 <br>");
        template.append(memberEmail.getEmailCode());
        return template.toString();
    }

    public String changePwTemplate(String memberPw) {
        StringBuffer template = new StringBuffer();

        template.append("<h1> MemoryBoost 비밀번호 찾기 </h1>");
        template.append("변경된 비밀번호는 " + memberPw + "입니다.");
        template.append("<br>");
        template.append("비밀번호를 꼭 변경해주세요!");
        return template.toString();
    }

}
