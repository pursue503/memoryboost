$(document).ready(function() {
	$('#summernote').summernote({
		  height: 400,
		  minHeight: 400,
		  maxHeight: 400,
		  focus: true,
		  lang: "ko-KR",
		  callbacks: {
		    onImageUpload : function(files) {
		        uploadSummernoteImageFile(files[0], this);
		    }
		  }

	});
});

//이미지 첨부
function uploadSummernoteImageFile(file, editor) {
    alert("실행");
    data = new FormData();
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
            //항상 업로드된 파일의 url이 있어야 한다.
            alert(data);
            $(editor).summernote('insertImage', data);
        }
    });
}