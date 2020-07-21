//테스트. 리스트 그리드 변경
$(document).on("click", "button.test", function() {
    $("div.product-list").toggleClass("grid");
});

//페이징에 키워드 부여
/*$(document).ready(function() {
    $("input.pagingKeyword")[0].value = String(getParam("keyword"));
});*/

//필터조건, 레이아웃
$(document).on("click", ".btn.chkbox, .btn.layout", function(e) {
    console.dir("필터조건, 레이아웃");
});

//정렬
$(document).on("change", ".btn.order1", function(e) {
    console.dir("정렬");
})

//페이지
$(document).on("click", ".btn.page", function(e) {
    e.preventDefault();
    console.dir("페이지");
});

//장바구니 모두 추가
$(document).on("click", ".btn.cartall", function(e) {
    e.preventDefault();
    console.dir("장바구니 모두 추가");
});

//장바구니, 바로구매
$(document).on("click", ".btn.addcart, .btn.purchase", function(e) {
    e.preventDefault();
    console.dir("장바구니 / 바로구매")
});

//테스트
$(document).on("click", "#testform > input[type=submit]", function(e) {
    e.preventDefault();
    var form = $("#testform")[0];
    var params = new FormData(form);
    var temp = $("#ff, sf").serialize();

    console.dir(params);
    console.dir(temp);
    /*$.ajax({
        type : "GET",
        url : "/search-filter",
        data : params,
        processData: false,
        contentType: false,
    })
    .done(function(fragment) {
        console.dir(fragment);
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })*/
})