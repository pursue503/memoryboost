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
    data = new FormData();
    data.append("file", file);
    $.ajax({
        data : data,
        type : "POST",
        url : "/uploadSummernoteImageFile",
        contentType : false,
        processData : false,
        success : function(data) {
            //항상 업로드된 파일의 url이 있어야 한다.
            $(editor).summernote('insertImage', data.url);
        }
    });
}