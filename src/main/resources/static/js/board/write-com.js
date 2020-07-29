$(document).ready(function() {
    /* 작성취소 */
    $(document).on("click", "#write-cancel", function(e) {
        e.preventDefault();
        var cancelConfirm = confirm("작성을 취소 하시겠습니까?");
        if(cancelConfirm){
            history.go(-1);
        }
    });

    /* 수정취소 */
    $(document).on("click", "#edit-cancel", function(e) {
        e.preventDefault();
        var cancelConfirm = confirm("수정을 취소 하시겠습니까?");
        if(cancelConfirm){
            history.go(-1);
        }
    });

    /* 수정완료 */
    $(document).on("click", "#edit-complete", function(e) {
        e.preventDefault();
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var postNo = e.target.value;
        var form = $("#write-form")[0];
        var params = new FormData(form);

        $.ajax({
            type : "put",
            url : "/post",
            data : params,
            processData: false,
            contentType: false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        })
        .done(function(response) {
            location.replace("/post/detail?postNo="+response);
        })
        .fail(function(response) {
            console.dir("통신 실패[일반게시판:수정]");
        });
    });
});