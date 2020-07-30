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
    $(document).on("click", "#post-edit-complete", function(e) {
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

    /* 댓글 삭제 */
    $(document).on("click", "button.btn-delete-reply", function(e) {
        var deleteFlag = confirm("댓글을 삭제 하시겠습니까?");

        if(deleteFlag) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var replyNo = e.target.value;
            //var postNo = $("#postNo")[0].value;
            $.ajax({
                type : "delete",
                url : "/reply",
                data : {  replyNo : replyNo },
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            })
            .done(function(response) {
                if(response) {
                    location.reload(true);
                    //location.replace("/post?category="+postNo);
                } else {
                    alert("삭제 실패");
                }
            })
            .fail(function(response) {
                console.dir("통신 실패");
            });
        }
    });
});