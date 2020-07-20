//테스트. 리스트 그리드 변경
$(document).on("click", "button.test", function() {
    $("div.product-list").toggleClass("grid");
});

//페이징에 키워드 부여
$(document).ready(function() {
    $("input#pagingKeyword")[0].value = String(getParam("keyword"));
});

//상세페이지로 이동
$(document).on("click", "")