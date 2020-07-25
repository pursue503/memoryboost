$(document).ready(function() {
    /*if(document.referrer.indexOf("cart") == -1) {
        console.dir("바로 구매를 누르셨군");
    } else {
        console.dir("여러개 주문이군");
    }*/
    //배송정보 초기화
    $(document).on("click", "#new-addr", function(e) {
        let info = $("div.shipment-info input[type=text]");

        for(let a of info) {
            a.value = null;
        }
        $("#membername")[0].focus();
    });

    //결제방법 선택
    $(document).on("click", "div.method > label", function(e) {
        $("div.method > label").removeClass("selected");
        $(this).addClass("selected");

        if($(this)[0].innerHTML == "카카오페이") {
            $("#cash-name")[0].disabled = true;
            $("div.cash").removeClass("pop");
        } else {
            $("div.cash").addClass("pop");
            $("#cash-name")[0].disabled = true;
            $("#cash-name")[0].disabled = false;
        }
    });

    //은행 라벨
    $(document).on("click", "#bank-label", function(e) {
        $("select.bank")[0][0].selected = true;
    });
    //은행 선택
    $(document).on("change", "select.bank", function(e) {
        let classname = e.target.value;

        //$("input#"+classname)[0].checked = true;
        $("input#"+classname)[0].click();
    });

    $(document).on("click", "input[name=orderPaymentGb]", function(e) {
        let tag = "<input type='hidden' name='bankNo' value='"+e.target.value+"' />";

        $("div#bank").empty();
        if(e.target.value > 0) {
            $("div#bank").append(tag);
        }
    });


    //결제버튼 누르면 결제팝업을 띄움
    $(document).on("click", "button.order", function(e) {
        e.preventDefault();
        let payMethod = $("input[type=radio][name=orderPaymentGb]:checked")[0].value;
        //let regex = /[^0-9]/g;
        //let totalPrice = Number($("#total-price")[0].innerText.replace(regex,""));
        let totalPrice = Number($("#total-price")[0].value);
        let params = {};

        params.totalAmount = totalPrice;

        if(payMethod == 0) {
            console.dir("카카오페이");
            $.ajax({
                type : "GET",
                url : "/kakaopay-ready",
                data : params
            })
            .done(function(response) {
                window.open(response, 'kakaopay', 'width=500, height=500');
            })
            .fail(function(response) {
                console.dir("통신 실패");
            })
        } else {
            console.dir("무통장입금");
            $("#order-form").submit();
        }
    });
});