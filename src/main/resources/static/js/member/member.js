//비밀번호 검증
var validatePassword = function(password, passwordConfirm) { //(패스워드, 패스워드확인)
    let regPw = /^[a-zA-Z0-9]{6,}$/ //정규식

    //일치 검증
    if(password.value === passwordConfirm.value) {
        //정규식 검증
        if(regPw.test(password.value) || regPw.test(passwordConfirm.value)) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
};

var validatePassword2 = function(password, passwordConfirm) { //(패스워드, 패스워드확인)
    console.dir("비번검증");
    let regPw = /^[a-zA-Z0-9]{6,}$/ //정규식
}

//이름 검증
var validateName = function(name) {
    return !isEmpty(name.value);
};

//주소 검증
var validateDetailAddress = function(detailaddr) {
    return !isEmpty(detailaddr.value);
}