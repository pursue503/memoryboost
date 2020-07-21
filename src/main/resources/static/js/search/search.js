//필터 펼치기-접기
$(document).on("click", "div#spreader", function(e) {
    $("div.filter").toggleClass("spread");
});

//카테고리
$(document).on("click", ".category-radio", function(e) {
    let selected = e.target.value;
    let criteria = $("div.criteria");
    let criteriaChk = $("input.btn.chkbox");

    for(let obj of criteriaChk) {
        obj.checked = true;
        obj.checked = false;
    }

    for(let obj of criteria) {
        obj.style.display = "none";
    }

    $("div.criteria."+selected).css("display", "block");

    var params = $("#form1").serialize();

    $.ajax({
        type : "GET",
        url : "/search-filter",
        data : params,
        processData : false,
        contentType : false
    })
    .done(function(fragment) {
        $("div.fragment-result").replaceWith(fragment);
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })
});

//필터조건, 레이아웃
$(document).on("click", ".btn.chkbox, .btn.layout", function(e) {
    console.dir("필터조건, 레이아웃");
    var params = $("#form1").serialize();
    let tempPage = $("input[type=hidden][name=tempPage]")[0].value;
    let keyword = $("input[type=hidden][name=keyword]")[0].value;
    let requestURL = "/search-filter";
    params += "&page="+tempPage;

    if(!isEmpty(keyword)) {
        requestURL = "/search-fragment";
    }
    $.ajax({
        type : "GET",
        url : requestURL,
        data : params,
        processData : false,
        contentType : false
    })
    .done(function(fragment) {
        $("div.fragment-result").replaceWith(fragment);
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })
});

//정렬
$(document).on("change", ".btn.order1", function(e) {
    var params = $("#form1").serialize();
    let tempPage = $("input[type=hidden][name=tempPage]")[0].value;
    let keyword = $("input[type=hidden][name=keyword]")[0].value;
    let requestURL = "/search-filter";
    params += "&page="+tempPage;

    if(!isEmpty(keyword)) {
        requestURL = "/search-fragment";
    }
    $.ajax({
        type : "GET",
        url : requestURL,
        data : params,
        processData : false,
        contentType : false
    })
    .done(function(fragment) {
        $("div.fragment-result").replaceWith(fragment);
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })
})

//페이지
$(document).on("click", ".btn.page", function(e) {
    e.preventDefault();
    var params = $("#form1").serialize();
    let keyword = $("input[type=hidden][name=keyword]")[0].value;
    let requestURL = "/search-filter";
    params += "&page="+e.target.value;

    if(!isEmpty(keyword)) {
        requestURL = "/search-fragment";
    }
    $.ajax({
        type : "GET",
        url : requestURL,
        data : params,
        processData : false,
        contentType : false
    })
    .done(function(fragment) {
        $("div.fragment-result").replaceWith(fragment);
        window.scrollTo(0, 0);
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })
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
