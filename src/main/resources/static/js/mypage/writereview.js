$(document).ready(function() {
    var productNo = Number(getParam("productNo"));
    var productName = decodeURI(getParam("productName"));

    $("a")[0].href = "/product-detail/"+productNo;
    $("#productNo")[0].value = productNo;
    $("#productName")[0].innerHTML = productName;

    //평가
    $(document).on("click", "div.star", function(e){
        $("div.star").removeClass("fixed");
        $(this).prevAll().addClass("fixed");
        $(this).addClass("fixed");
        $("#grade")[0].value = e.target.dataset.value;
    });

    //보내기(ajax로 true false 체크 후)
    $(document).on("click", "#submit", function(e) {
        e.preventDefault();
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        let form = $("#review-form")[0];
        let params = new FormData(form);

        $.ajax({
            type : "POST",
            url : "/review",
            data : params,
            processData : false,
            async : true,
            cache : false,
            contentType: false,
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        })
        .done(function(response) {
            if(response.result) {
                alert("리뷰 작성 완료!");
                opener.href.replace("/");
                window.close();
            } else {
                alert("리뷰 작성에 실패.. 다시 시도해주세요.");
            }
        })
        .fail(function(response) {
            console.dir("통신 실패");
        })
    });
});

opener.popup = this;