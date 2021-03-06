package com.memoryboost.domain.dto.member.memoryboost.request;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveRequestDTO {

    private String memberLoginId;
    private String memberPw;
    private String memberName;
    private String memberTel;
    private String memberZipCode;
    private String memberAddress;
    private String memberDetailAddress;
    private String memberEmail;

    //유효성 검사
    public boolean patternCheck() {

        Pattern id = Pattern.compile("^[a-z]+[a-z0-9]{3,11}$");
        Matcher idMatcher = id.matcher(this.memberLoginId);
        Pattern pw = Pattern.compile("^(?=.*?[^\\s])[\\w\\d]{6,12}$");
        Matcher pwMatcher = pw.matcher(this.memberPw);
        Pattern email = Pattern.compile("^[\\d\\w]+@(=?.*?[\\w]+)[\\d\\w]*\\.[\\w]+(\\.[\\w]+){0,1}$");
        Matcher emailMatcher = email.matcher(this.memberEmail);
        Pattern tel = Pattern.compile("^(0[0-9]{1,2}|01[0-9]{1})-([0-9]{3,4})-([0-9]{4})$");
        Matcher telMatcher = tel.matcher(this.memberTel);
        //find = 같다면 true
        if(idMatcher.find()) {
            if(pwMatcher.find()) {
                if(emailMatcher.find()){
                    if(telMatcher.find()) {
                        if(!memberZipCode.equals("") && memberZipCode != null) {
                            if(!memberAddress.equals("") && memberAddress != null) {
                                if(!memberDetailAddress.equals("") && memberDetailAddress != null) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public Member toEntity(){
        return Member.builder()
                .memberLoginId(memberLoginId)
                .memberPw(memberPw)
                .memberName(memberName)
                .memberTel(memberTel)
                .memberZipCode(memberZipCode)
                .memberAddress(memberAddress)
                .memberDetailAddress(memberDetailAddress)
                .memberSns("memoryboost")
                .memberAuth(Role.USER)
                .memberEmail(memberEmail)
                .build();
    }


}
