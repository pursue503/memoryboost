package com.memoryboost.domain.dto.member.memoryboost.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberUpdateRequestDTO {

    private String memberPw;
    private String memberName;
    private String memberTel;
    private String memberZipCode;
    private String memberAddress;
    private String memberDetailAddress;

    //유효성 검사
    public boolean patternCheck() {

        Pattern pw = Pattern.compile("^(?=.*?[^\\s])[\\w\\d]{6,12}$");
        Matcher pwMatcher = pw.matcher(this.memberPw);
        Pattern tel = Pattern.compile("^(0[0-9]{1,2}|01[0-9]{1})-([0-9]{3,4})-([0-9]{4})$");
        Matcher telMatcher = tel.matcher(this.memberTel);
        //find = 같다면 true
        if(pwMatcher.find()) {
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

        return false;
    }



}
