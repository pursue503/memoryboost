$(document).ready(function() {
    //필터
    $(document).on("click", ".btn.chkbox", function(e) {
        $(".page[value=1]")[0].click();
    });

    //검색어
    $(document).on("keypress", "#estimate-keyword", function(e) {
        if(e.which == 13)  {
            e.preventDefault();
            $(".page[value=1]")[0].click();
        }
    });

    $(document).on("keyup", "#estimate-keyword", function(e) {
        if(!isEmpty(e.target.value)) {
            $("button.clear-keyword").removeClass("notseen");
        } else {
            $("button.clear-keyword").addClass("notseen");
        }
    });

    //정렬
    $(document).on("change", "select[name=order]", function(e) {
        $(".page[value=1]")[0].click();
    });

    //페이지
    $(document).on("click", ".btn.page", function(e) {
        e.preventDefault();

        let form = $("#search-form");
        let params = form.serialize();
        //let form = $("#search-form")[0];
        //let params = new FormData(form);
        params += "&page="+e.target.value;

        $.ajax({
            type : "GET",
            url : "/estimate-fragment-result",
            data : params,
            processData : false,
            contentType : false,
            beforeSend : function() {
                $("div.product-list")[0].scrollTo({ top:0 });
                $("div.loader").remove();
                $("div.product-list").append("<div class='loader'><img src='/img/ajax-loader.gif' /></div>");
            }
        })
        .done(function(fragment){
            $("#replacethis").replaceWith(fragment);
            let leftButtons = $("div.left div.buttons > .btn");
            let rightAdded = $("div.right div.added");

            //추가된 제품 버튼 변경
            for(let product of leftButtons) {
                for(let added of rightAdded) {
                    if(product.dataset.productnum == added.dataset.productnum) {
                        //제거 버튼으로 변경
                        product.classList.remove("add");
                        product.innerHTML = "제거";
                        product.classList.add("remove");
                    }
                }
            }
        })
        .fail(function(response){
            console.dir("통신 실패! [견적서:페이징]");
        })
    });

    //카테고리 선택
    $(document).on("click", "div.yourlist > h3", function(e) {
        let category = this.classList[0];

        $("div.yourlist > h3").removeClass("selected");
        $("div.category").removeClass("selected");
        $(this).addClass("selected");
        $("div.category."+category).addClass("selected");
        $("input#category")[0].value = category;


        //필터 교체
        $.ajax({
            type : "GET",
            url : "/filter-change",
            data : { category : category }
        })
        .done(function(fragment){
            $("div.criteria").replaceWith(fragment);
            $(".page[value=1]")[0].click();
        })
        .fail(function(response){
            console.dir("통신 실패! [견적서:필터교체]");
        })
    });

    //제품 추가
    $(document).on("click", ".btn.add", function(e) {
        e.preventDefault();
        let category = e.target.dataset.category;
        let productNo = e.target.dataset.productnum;

        //제거 버튼으로 변경
        $(this).toggleClass("add");
        if($(this)[0].innerHTML == "추가") {
            $(this)[0].innerHTML = "제거";
        } else {
            $(this)[0].innerHTML = "추가";
        }
        $(this).toggleClass("remove");

        //fragment
        $.ajax({
            type : "GET",
            url : "/add-fragment",
            data : { productNo : productNo,
                     category : category },
            beforeSend : function() {
                $("div.right > div.loader").remove();
                $("div.right").append("<div class='loader'><img src='/img/ajax-loader.gif' /></div>");
            }
        })
        .done(function(fragment){
            $("div.yourlist > div."+category).append(fragment);
            $("div.right > div.loader").remove();
        })
        .fail(function(response){
            console.dir("통신 실패! [견적서:제품추가]");
        })
    });

    //왼쪽 제거버튼
    $(document).on("click", "div.left .btn.remove", function(e) {
        //추가 버튼으로 변경
        $(this).toggleClass("add");
        if($(this)[0].innerHTML == "추가") {
            $(this)[0].innerHTML = "제거";
        } else {
            $(this)[0].innerHTML = "추가";
        }
        $(this).toggleClass("remove");
    });

    //제품 빼기
    $(document).on("click", ".btn.remove", function(e) {
        e.preventDefault();

        let category = e.target.dataset.category;
        let productNo = e.target.dataset.productnum;

        $("div.added."+productNo).remove();

        //현재 페이지에 있는 제품이면 추가버튼으로 변경
        let leftButtons = $("div.left div.buttons > .btn");
        let rightAdded = $("div.right div.added");
        for(let product of leftButtons) {
            if(product.dataset.productnum == productNo) {
                //제거 버튼으로 변경
                product.classList.remove("remove");
                product.innerHTML = "추가";
                product.classList.add("add");
            }
        }
    });

    //수량 플러스 마이너스 버튼
    $(document).on("click", ".btn.count", function(e) {
        e.preventDefault();
        let added = $("div.added."+e.target.dataset.productnum);
        let productNo = Number(e.target.dataset.productnum);
        let productCount = added.find("input.count")[0].value;

        if(e.target.classList[2] == "down" && productCount > 1) {
            added.find("input.count")[0].value = --productCount;
        } else if(e.target.classList[2] == "up") {
            added.find("input.count")[0].value = ++productCount;
        }
    });

    //수량 변경 시
    $(document).on("change", "input.count", function(e) {
        let added = $("div.added."+e.target.dataset.productnum);
        let productCount = added.find("input.count")[0].value;

        if(!$.isNumeric(productCount) || productCount < 1) {
            alert("수량은 1 이상만 입력 가능합니다.");
            added.find("input.count")[0].value = 1;
        }
    });

    //추가된 제품 초기화
    $(document).on("click", "#reset", function(e) {
        e.preventDefault();

        let inquiry = confirm("견적서를 초기화 하시겠습니까?");
        if(inquiry){
            $("div.added").remove();
            $("h3.cpu")[0].click();
        }
    });

    //주문하기
    $(document).on("click", "#order", function(e) {
        e.preventDefault();
        let added = $("div.added");
        let orderList = new Array();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        if(added.length) {
            for(let obj of added) {
                let productNo = obj.dataset.productnum;
                let productCount = $(obj).find("input.count")[0].value;
                let product = new Object();
                product.productNo = Number(productNo);
                product.productCnt = Number(productCount);
                orderList.push(product);
            }

            $.ajax({
                type : "POST",
                url : "/order/estimate",
                data : JSON.stringify(orderList),
                contentType : "application/json",
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                }
            })
            .done(function(response) {
                if(response) {
                    location.href="/order/estimate";
                }
            })
            .fail(function(response) {
                console.dir("통신 실패");
            });
        } else {
            alert("견적서가 비어있습니다.");
        }
    });

    //가격 체크 및 변경
    setTotalPrice();
});

//상품 상세정보 띄우기
function productDetail(productNo) {
    let URL = "/product-detail/"+productNo;
    window.open(URL);
}

//가격 계속 체크
function setTotalPrice() {
    setInterval(execute, 100);

    function execute() {
        let added = $("div.added");
        let totalPrice = 0;
        for(let obj of added) {
            let productPrice = $(obj).find(".product-price")[0].dataset.price;
            let productCount = $(obj).find("input.count")[0].value;
            totalPrice += (productPrice * productCount);
        }
        $(".total-price")[0].innerHTML = totalPrice.toLocaleString()+"원";
    }
}