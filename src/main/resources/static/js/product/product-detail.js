var controlTabOffset = 0;
$(document).ready(function() {
    controlTabOffset = document.querySelector("div.control-tab").offsetTop; //인디케이터 바닥
});

//수량 플러스 마이너스 버튼
$(document).on("click", ".btn.amount", function(e) {
    let productAmount = $("input.amount")[0].value;
    let productPrice = Number($("div.interaction > span.price")[0].dataset.value);
    let totalPrice = $("div.total-price > div.price")[0];

    if(e.target.classList[2] == "down" && productAmount > 0) {
        $("input.amount")[0].value = --productAmount;
        totalPrice.innerHTML = (productPrice * productAmount).toLocaleString()+"원";
    } else if(e.target.classList[2] == "up") {
        $("input.amount")[0].value = ++productAmount;
        totalPrice.innerHTML = (productPrice * productAmount).toLocaleString()+"원";
    }
});

//수량 변경 시
$(document).on("change", "input.amount", function(e) {
    let productAmount = $("input.amount")[0].value;
    let productPrice = Number($("div.interaction > span.price")[0].dataset.value);
    let totalPrice = $("div.total-price > div.price")[0];

    if(!$.isNumeric(productAmount) || productAmount < 0) {
        alert("수량은 0 이상만 입력 가능합니다.");
        $("input.amount")[0].value = 0;
        totalPrice.innerHTML = "0원";
    } else {
        totalPrice.innerHTML = (productPrice * productAmount).toLocaleString()+"원";
    }
});

//컨트롤 패널
$(document).on("click", "div.btn.control", function(e) {
    let descriptionOffset = document.querySelector("div.description-image").offsetTop;
    let reviewOffset = document.querySelector("div.product-review").offsetTop;
    let shipmentOffset = document.querySelector("div.shipment-guide").offsetTop;

    if(e.target.classList[2] == 'detail') {
        window.scrollTo({top:descriptionOffset-35, behavior:'smooth'});
    } else if(e.target.classList[2] == 'review') {
        window.scrollTo({top:reviewOffset, behavior:'smooth'});
    } else if(e.target.classList[2] == 'shipment') {
        window.scrollTo({top:shipmentOffset, behavior:'smooth'});
    }
});

//스크롤 이벤트
$(document).on("scroll", function(e) {
    let currentScroll = window.scrollY;
    let descriptionOffset = Number(document.querySelector("div.description-image").offsetTop);
    let reviewOffset = Number(document.querySelector("div.product-review").offsetTop);
    let shipmentOffset = Number(document.querySelector("div.shipment-guide").offsetTop);

    if(currentScroll > descriptionOffset) {
        $(".control-tab").addClass("onfix");
    } else {
        $(".control-tab").removeClass("onfix");
    }

    if(currentScroll < reviewOffset) {
        $("div.indicator")[0].className = "indicator detail";
    } else if(currentScroll < shipmentOffset && currentScroll > reviewOffset) {
        $("div.indicator")[0].className = "indicator review";
    } else if(currentScroll >= shipmentOffset) {
        $("div.indicator")[0].className = "indicator shipment";
    }

});

//리뷰 게시판 내용 토글
$(document).on("click", "td.review-title", function(e) {
    $("tr.content-row").removeClass("pop");
    $("td.review-title").removeClass("pop");

    $("td.review-title."+e.target.classList[1]).toggleClass("pop");
    $("tr.content-row."+e.target.classList[1]).toggleClass("pop");
});

$(document).on("click", "td.review-title.pop", function(e) {
    $("tr.content-row").removeClass("pop");
    $("td.review-title").removeClass("pop");
});

//리뷰 게시판 정렬
$(document).on("click", "div.order > input[type=radio]", function(e) {
    let productNum = $("#product-number")[0].value;
    let params = $("#review-form").serialize();
    let requestURL = "/product/detail/"+productNum+"/fragment";

    $.ajax({
        type : "GET",
        url : requestURL,
        data : params,
        processData : false,
        contentType : false
    })
    .done(function(fragment) {
        $("div#fragment").replaceWith(fragment);
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })
});

//리뷰 게시판 페이징
$(document).on("click", ".btn.page", function(e) {
    e.preventDefault();
    let productNum = $("#product-number")[0].value;
    let pageNum = e.target.value;
    let params = $("#review-form").serialize();
    let requestURL = "/product/detail/"+productNum+"/fragment";

    params += "&page="+pageNum;

    $.ajax({
        type : "GET",
        url : requestURL,
        data : params,
        processData : false,
        contentType : false
    })
    .done(function(fragment) {
        $("div#fragment").replaceWith(fragment);
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })
});

//장바구니
$(document).on("click", "button.cart", function(e) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    let productNum = Number($("#product-number")[0].value);
    let productAmount = Number($("#amount")[0].value);

    if(productAmount == 0) {
        alert("수량을 입력해주세요");
        return;
    }

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
                location.href = "/members/mypage";
            }
        }
    })
    .fail(function(response) {
        console.dir("통신 실패");
    })
});
