$(document).ready(function() {
    //전체노드 탐색 후 liftup 효과 적용
    var elems = document.body.getElementsByTagName("*");
    for(let a of elems) {
        if(a.classList.contains("liftup")) {
            a.classList.add("lifted");
        }
    }

    //회원가입 완료(성공)
    $(document).on("click", ".signup-button", function(e) {
        e.preventDefault();
        if($("#condition")[0].checked) {
            alert("회원가입에 성공했습니다. 이메일 인증을 해주세요.");
            $("#hidden-submit").click();
        } else {
            alert("이용약관에 동의해주세요");
            return;
        }
    });
});

//주소검색 API
function searchZipCode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수
            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }
            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
            }
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}

//isEmpty() 구현
var isEmpty = function(val) {
    if(val === "" || val === null || val === undefined
    || (val !== null && typeof val === "object" && !Object.keys(val).length)) {
        return true
    } else {
        return false
    }
};

//회원가입 검증
var validateSignup = function() {
    //요소들
    let memberLoginId = document.getElementById("memberLoginId");
    let memberPw = document.getElementById("memberPw");
    let memberPwConfirm = document.getElementById("memberPwConfirm");
    let memberName = document.getElementById("memberName");
    let memberEmail = document.getElementById("memberEmail");
    let memberTel = document.getElementById("memberTel");
    let memberZipcode = document.getElementById("zipcode");
    let memberAddress = document.getElementById("address");
    let memberDetailAddress = document.getElementById("detailAddress");
    //아이콘
    let statusId = document.querySelector(".chksum.id");
    let statusPw = document.querySelector(".chksum.pw");
    let statusName = document.querySelector(".chksum.name");
    let statusEmail = document.querySelector(".chksum.email");
    let statusTel = document.querySelector(".chksum.tel");
    let statusDetailAddr = document.querySelector(".chksum.detailaddr");
    //정규식
    let reId = /^[a-zA-Z0-9]{4,12}$/ //아이디
    let rePw = /^[a-zA-Z0-9]{6,}$/ //비밀번호
    let reEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; //이메일
    let reTel = /^(0[0-9]{1,2}|01[0-9]{1})-([0-9]{3,4})-([0-9]{4})$/g; //전화번호
    //아이디
    if(isEmpty(memberLoginId.value) || !reId.test(memberLoginId.value)) {
        statusId.classList.remove("true");
        statusId.classList.add("false");
    } else {
        chkIdDuplicate(memberLoginId);
    }
    //비밀번호
    if(isEmpty(memberPw.value) || isEmpty(memberPwConfirm.value) || !rePw.test(memberPw.value) || !rePw.test(memberPwConfirm.value) || (memberPw.value !== memberPwConfirm.value)) {
        //불가능
        statusPw.classList.remove("true");
        statusPw.classList.add("false");
    } else {
        //가능
        statusPw.classList.remove("false");
        statusPw.classList.add("true");
    }
    //이름
    if(isEmpty(memberName.value)) {
        statusName.classList.remove("true");
        statusName.classList.add("false");
    } else {
        statusName.classList.remove("false");
        statusName.classList.add("true");
    }
    //이메일
    if(isEmpty(memberEmail.value) || !reEmail.test(memberEmail.value)) {
        statusEmail.classList.remove("true");
        statusEmail.classList.add("false");
    } else {
        statusEmail.classList.remove("false");
        statusEmail.classList.add("true");
    }
    /* 전화번호 */
    if(isEmpty(memberTel.value) || !reTel.test(memberTel.value)) {
        statusTel.classList.remove("true");
        statusTel.classList.add("false");
    } else {
        statusTel.classList.remove("false");
        statusTel.classList.add("true");
    }
    //상세주소
    if(isEmpty(memberDetailAddress.value) || isEmpty(memberZipcode.value) || isEmpty(memberAddress.value)) {
        statusDetailAddr.classList.remove("true");
        statusDetailAddr.classList.add("false");
    } else {
        statusDetailAddr.classList.remove("false");
        statusDetailAddr.classList.add("true");
    }
    //회원가입 버튼 활성화 여부
    let chkComplete = document.querySelectorAll(".chksum.false");
    if(chkComplete.length) {
        document.getElementById("submit").disabled = true;
    } else {
        document.getElementById("submit").disabled = false;
    }
}

//아이디 중복 체크(ajax)
var chkIdDuplicate = function(val) {
    var httpRequest;

    doRequest(val);

    function doRequest(id) {
        httpRequest = new XMLHttpRequest();

        if(!httpRequest) {
            console.dir("XMLHttp 인스턴스 생성 불가능");
            return false;
        }
        httpRequest.onreadystatechange = alertContents;
        let chkId = document.getElementById("memberLoginId").value;
        let requestTo = "/members/"+chkId+"/overlap/check";
        httpRequest.open("GET", requestTo);
        httpRequest.send();

        function alertContents() {
            let status = document.querySelector(".chksum.id");
            if(httpRequest.readyState === XMLHttpRequest.DONE) {
                let result = httpRequest.response;
                let chkId = document.getElementById("memberLoginId");
                if(httpRequest.status === 200) {
                    if(result.indexOf("true") > 0) { //아이디 사용 불가능
                        status.classList.remove("true");
                        status.classList.add("false");
                    } else { //아이디 사용 가능
                        status.classList.remove("false");
                        status.classList.add("true");
                    }
                } else {
                    console.dir("request failed");
                }
            }
        }
    }
}

//아이디 찾기 (email로 찾기, ajax)
var findId = function() {
    let memberEmail = document.getElementById("memberEmail");
    $.ajax({
        url: "/members/findid/"+memberEmail.value
    })
    .done(function(response) {
        var tag = "<table>"
                       +"<tr>"
                           +"<td>아이디</td>"
                           +"<td>가입일</td>"
                       +"</tr>";
        for(let i in response) {
            tag += "<tr>"
                        +"<td class='auto-input'>"
                            +response[i].memberLoginId
                        +"</td>"
                        +"<td>"
                            +response[i].memberSignupDatel
                        +"</td>"
                   "</tr>"
        }
        tag += "</table>";
        $(".id-list").empty().append(tag);
    })
    .fail(function(response) {
        return false;
    });
}

//표현 자릿수 변환 (원본숫자, 채울숫자?, 몇자리로?)
var convertDigit = function(num, pWith, pLength) {
    let pBlock = ""; //추가할 블록
    let result = num+""; //결과

    if(result.length > pLength) {
        return false;
    }
    for(var index=0;index<pLength;index++) {
        pBlock += pWith;
    }
    result = (pBlock + result).slice(-pLength);

    return result;
}

//타이머
var deployTimer = function(t, deployTo) {
    let time = t;
    let min = "";
    let sec = "";
    var intervalId = setInterval(function() {
        min = parseInt(time/60);
        sec = time % 60;
        deployTo.innerHTML = convertDigit(min, 0, 2)+":"+convertDigit(sec, 0, 2);
        time--;

        if(time < 0) {
            clearInterval(intervalId);
            deployTo.innerHTML = "만료됨";
        }
    }, 1000);
}

//Jquery serialize into map
jQuery.fn.serializeObject = function() {
    var obj = null;
    try {
        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
            var arr = this.serializeArray();
            if (arr) {
                obj = {};
                jQuery.each(arr, function() {
                    obj[this.name] = this.value;
                });
            }//if ( arr ) {
        }
    } catch (e) {
        alert(e.message);
    } finally {
    }

    return obj;
};

//url에서 어느 게시판인지 추출
function getBoardName() {
    var params = location.pathname.split("/")[2];

    return params;
}

//url 에서 parameter 추출
function getParam(sname) {
    var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";

    params = params.split("&");

    for (var i = 0; i < params.length; i++) {
        temp = params[i].split("=");
        if ([temp[0]] == sname) { sval = temp[1]; }
    }

    return sval;
}