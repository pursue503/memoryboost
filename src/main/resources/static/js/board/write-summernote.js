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

    //이미지 첨부
    function uploadSummernoteImageFile(file, editor) {
        alert("파일 업로드는 되는것인가 1");
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
            url : "/post/image-upload",
            contentType : false,
            processData : false,
            beforeSend : function(xhr) {
                alert("파일 업로드는 되는것인가 2");
                xhr.setRequestHeader(header, token);
            },
            success : function(data) {
                alert("파일 업로드는 되는것인가 3");
                $(editor).summernote('insertImage', data);
                if(!isEmpty(data)) {
                    alert("파일 업로드는 되는것인가 4");
                    let tag = "<input type='hidden' name='file' value='"+data+"' />"
                    $("#write-form").append(tag);
                }
            }
        });
    }
});

