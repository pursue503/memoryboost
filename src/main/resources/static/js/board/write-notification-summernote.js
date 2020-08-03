$(document).ready(function() {
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

    //저장
    $(document).on("click", "#notice-complete", function(e) {
        e.preventDefault();
        let postTitle = $("input.title")[0].value;
        let postContent = $("#summernote")[0].value.replace(/<p>/gi,"").replace(/<\/p>/gi,"").replace(/<br>/gi,"").replace(/&nbsp;/gi,"").replace(/\s/gi, "");

        let submitFlag = true;
        if(isEmpty(postTitle) || isEmpty(postContent)) {
            submitFlag = false;
        }

        if(submitFlag) {
            $("#write-form").submit();
        } else {
            alert("내용을 입력해주세요.");
            return;
        }
    });

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
                $(editor).summernote('insertImage', data);
                if(!isEmpty(data)) {
                    let tag = "<input type='hidden' name='path' value='"+data+"' />"
                    $("#write-form").append(tag);
                    console.dir(data);
                }
            }
        });
    }
});

