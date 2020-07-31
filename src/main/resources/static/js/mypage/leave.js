function proceedLeave() {
    var leaveFlag = confirm("회원 탈퇴를 진행합니다");

    if(leaveFlag) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            method : "delete",
            url : "/members",
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header,token);
            }
        })
        .done(function(response) {
            if(response.result) {
                alert("회원탈퇴 완료");
                location.href = "/members/logout";
            } else {
                alert("회원탈퇴 실패. 다시 시도해주세요.");
            }
        })
        .fail(function(response) {
            console.dir("통신 실패");
        });
    }
}