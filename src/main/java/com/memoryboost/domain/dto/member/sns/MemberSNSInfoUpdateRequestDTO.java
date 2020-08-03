package com.memoryboost.domain.dto.member.sns;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class MemberSNSInfoUpdateRequestDTO {

    private String memberEmail;
    private String memberTel;
    private String memberZipCode;
    private String memberAddress;
    private String memberDetailAddress;

    public boolean patternCheck() {

        Pattern email = Pattern.compile("^[\\d\\w]+@(=?.*?[\\w]+)[\\d\\w]*\\.[\\w]+(\\.[\\w]+){0,1}$");
        Matcher emailMatcher = email.matcher(this.memberEmail);
        Pattern tel = Pattern.compile("^(0[0-9]{1,2}|01[0-9]{1})-([0-9]{3,4})-([0-9]{4})$");
        Matcher telMatcher = tel.matcher(this.memberTel);
        if(emailMatcher.find()) {
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
    } // end

}
