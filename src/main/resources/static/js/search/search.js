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

    let chkbox = $(".cartchk");
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var params = new Array();

    for(let a of chkbox) {
        if(a.checked) {
            let obj = {
                productNo : Number(a.dataset.num),
                productCnt : 1
            };

            params.push(obj);
        }
    }

    $.ajax({
        type : "POST",
        url : "/cart",
        data : JSON.stringify(params),
        processData : false,
        contentType: "application/json",
        async : true,
        cache : false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    })
    .done(function(response) {
        if(response.result == undefined) {
            let toSignin= confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?");
            if(toSignin){
                location.href = "/members/signin";
            }
        } else if(response.result == true) {
            let toCartPage = confirm("장바구니에 추가했습니다. 장바구니로 이동하시겠습니까?");
            if(toCartPage) {
                location.href = "/mypage/cartList";
            }
        }
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })
});

//장바구니
$(document).on("click", ".btn.addcart", function(e) {
    e.preventDefault();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    let productNum = Number(e.target.dataset.num);
    let productAmount = 1;

    var params = new Array();

    let obj = {
        productNo : productNum,
        productCnt : productAmount
    };

    params.push(obj);

    $.ajax({
        type : "POST",
        url : "/cart",
        data : JSON.stringify(params),
        processData : false,
        contentType: "application/json",
        async : true,
        cache : false,
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    })
    .done(function(response) {
        if(response.result == undefined) {
            let toSignin= confirm("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?");
            if(toSignin){
                location.href = "/members/signin";
            }
        } else if(response.result == true) {
            let toCartPage = confirm("장바구니에 추가했습니다. 장바구니로 이동하시겠습니까?");
            if(toCartPage) {
                location.href = "/mypage/cartList";
            } else {
                location.reload();
            }
        }
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })
});

//구매
$(document).on("click", ".btn.purchase", function(e) {
    e.preventDefault();
    tag = "<input type='hidden' name='productNo' value='"+e.target.dataset.num+"' />";
    $("#order-single").append(tag);
    $("#order-single").submit();
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
