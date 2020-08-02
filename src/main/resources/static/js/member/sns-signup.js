$(document).ready(function() {
    var reEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; //이메일

    if(!isEmpty($("#memberEmail")[0].value)) {
        $(".chksum.email").removeClass("false");
        $(".chksum.email").addClass("true");
    }
    //이메일
    $(document).on("keyup", "#memberEmail", function(e) {
        if(isEmpty(memberEmail.value) || !reEmail.test(memberEmail.value)) {
            $(".chksum.email").removeClass("true");
            $(".chksum.email").addClass("false");
        } else {
            $(".chksum.email").removeClass("false");
            $(".chksum.email").addClass("true");
        }
        if(isEmpty($("input#detailAddress")[0].value)) {
            document.getElementById("submit").disabled = true;
        } else {
            document.getElementById("submit").disabled = false;
        }
    });

    //상세주소
    (function() {
        document.getElementById("detailAddress").addEventListener("keyup", function() {
            let memberDetailAddress = document.getElementById("detailAddress");
            let statusDetailAddr = document.querySelector(".chksum.detailaddr");
            if(isEmpty(memberDetailAddress.value)) { //비어있음
                statusDetailAddr.classList.remove("true");
                statusDetailAddr.classList.add("false");
                document.getElementById("submit").disabled = true;
            } else { //비어있지 않음
                statusDetailAddr.classList.remove("false");
                statusDetailAddr.classList.add("true");
                document.getElementById("submit").disabled = false;
            }
        });
    })();

    $(document).on("click", "span.condition", function(e) {
        e.preventDefault();
    });

    $(document).on("click", "#submit", function(e) {
        e.preventDefault();

        if(!$("input#condition")[0].checked) {
            alert("이용약관에 동의해주세요.");
            return;
        }

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        let form = $("form#signup-form")[0];
        let params = new FormData(form);
        $.ajax({
            type : "PUT",
            url : "/members/sns",
            data : params,
            processData: false,
            contentType: false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header,token);
            }
        })
        .done(function(response) {
            if(response) {
                alert("회원가입을 완료했습니다! 다시 로그인 해주세요.");
                location.replace("/");
            }
        })
        .fail(function(response) {
            console.dir("통신 실패");
        })
    });
});