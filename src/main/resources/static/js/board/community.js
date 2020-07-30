$(document).ready(function() {
    var temp = document.getElementsByClassName("title");
    shave(temp, 50);

    /* 글 수정 */
    $(document).on("click", "button.post-edit", function(e) {
        var editFlag = confirm("글을 수정 하시겠습니까?");

        if(editFlag) {
            location.href = "/post/update?postNo="+e.target.value;
        }
    });

    $(document).on("click", "button.notice-edit", function(e) {
        var editFlag = confirm("글을 수정 하시겠습니까?");

        if(editFlag) {
            location.href = "/notice/update?noticeNo="+e.target.value;
        }
    });

    /* 글 삭제 */
    $(document).on("click", "button.post-delete", function(e) {
        var deleteFlag = confirm("글을 삭제 하시겠습니까?");

        if(deleteFlag) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var category = $("input#category")[0].value;

            $.ajax({
                type : "delete",
                url : "/post",
                data : {  postNo : e.target.value },
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            })
            .done(function(response) {
                if(response) {
                    location.replace("/post?category="+category);
                } else {
                    alert("삭제 실패");
                }
            })
            .fail(function(response) {
                console.dir("통신 실패");
            });
        }
    });

    $(document).on("click", "button.notice-delete", function(e) {
        var deleteFlag = confirm("글을 삭제 하시겠습니까?");

        if(deleteFlag) {
            console.dir()
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var category = $("input#category")[0].value;

            $.ajax({
                type : "delete",
                url : "/notice",
                data : {  noticeNo : e.target.value },
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            })
            .done(function(response) {
                if(response) {
                    location.replace("/notice?category="+category);
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