$(document).ready(function() {
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
    $(document).on("click", "#submit", function(e) {
        e.preventDefault();
        alert("asd");
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        let form = $("form#signup-form")[0];
        let params = new FormData(form);
        $.ajax({
            type : "put",
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
                alert("회원가입을 완료했습니다!");
                location.replace("/");
            }
        })
        .fail(function(response) {
            console.dir("통신 실패");
        })
    });
    /*(function() {
        $(document).on("click", "#submit", function(e) {
            e.preventDefault();
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            let form = $("form#signup-form")[0];
            let params = new FormData(form);
            $.ajax({
                type : "put",
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
                    alert("회원가입을 완료했습니다!");
                    location.replace("/");
                }
            })
            .fail(function(response) {
                console.dir("통신 실패");
            })
        });
    })();*/
});