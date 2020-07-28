$(document).ready(function() {
    /* 게시판에 따른 행동 */
	lightNavigator();
    changeBoardName();

    //썸머노트 삽입
	$('#summernote').summernote({
		  height: 400,
		  minHeight: 400,
		  maxHeight: 400,
		  focus: false,
		  lang: "ko-KR",
		  callbacks: {
		    onImageUpload : function(files) {
		        uploadSummernoteImageFile(files[0], this);
		    }
		  }
	});

    /* 작성취소 */
    $(document).on("click", "#write-cancel", function(e) {
        e.preventDefault();
        var cancelConfirm = confirm("작성을 취소 하시겠습니까?");
        if(cancelConfirm){
            history.go(-1);
        }
    });
});
//제목 변경
function changeBoardName() {
    var boardName = getParam("board");
    var h1 = document.getElementsByTagName('h1')[0];

    if(boardName == "estimate") {
        h1.innerHTML = "견적요청";
        $("select.category").remove();
    } else if(boardName == "review") {
        h1.innerHTML = "이용후기";
    } else if(boardName == "update") {
        h1.innerHTML = "업데이트";
        $("select.category").remove();
    } else if(boardName == "event") {
        h1.innerHTML = "이벤트";
        $("select.category").remove();
    }
}

//게시판 이름 점등
function lightNavigator() {
    var boardName = getParam("board");

    var navigator = $("nav.community > button");

    navigator.removeClass("selected");
    $("nav.community > button."+boardName).addClass("selected");
}

//이미지 첨부
function uploadSummernoteImageFile(file, editor) {
    var typeFilter = ["jpg", "jpeg", "png", "gif"];
    var type = file.type.split("/")[1];
    var typeFlag = 0;
    for(let filter of typeFilter) {
        if(type == filter) {
            typeFlag = 1;
        }
    }

    if(typeFlag == 0) {
        alert("이미지는 [jpg, jpeg, png, gif]만 업로드 가능합니다.");
        return;
    }

    if(file.size > 2097152) {
        alert("이미지 크기는 2MB를 넘을 수 없습니다.");
        return;
    }

    var data = new FormData();
    data.append("file", file);

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    alert("ajax 실행");
    $.ajax({
        data : data,
        type : "POST",
        url : "/notice/image-upload",
        contentType : false,
        processData : false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success : function(data) {
            alert(data);
            //항상 업로드된 파일의 url이 있어야 한다.
            $(editor).summernote('insertImage', data);
            if(!isEmpty(data)) {
                let tag = "<input type='hidden' name='path' value='"+data+"' />"
                $("#write-form").append(tag);
            }
        }
    });
}