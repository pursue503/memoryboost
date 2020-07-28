$(document).ready(function() {
    //썸머노트 삽입
	$('#summernote').summernote({
		  height: 400,
		  minHeight: 400,
		  maxHeight: 400,
		  focus: false,
		  lang: "ko-KR"/*,
		  callbacks: {
		    onImageUpload : function(files) {
		        uploadSummernoteImageFile(files[0], this);
		    }
		  }*/
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