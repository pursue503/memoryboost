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
        let memberName = $("#membername")[0].value;
        let memberTel = $("#membertel")[0].value;
        let memberZipcode = $("#zipcode")[0].value;
        let memberAddress = $("#address")[0].value;
        let memberDetailAddress = $("#detailAddress")[0].value;

        if(isEmpty(memberName) || isEmpty(memberTel) || isEmpty(memberZipcode) || isEmpty(memberAddress) || isEmpty(memberDetailAddress)) {
            alert("배송정보를 모두 입력해주세요");
            return;
        }

        let payMethod = $("input[type=radio][name=orderPaymentGb]:checked")[0].value;
        let totalPrice = Number($("#total-price")[0].value);

        let params = {};

        params.totalAmount = totalPrice;

        if(payMethod == 0) {
            if(totalPrice > 1000000) {
                alert("죄송합니다. 카카오페이 API 테스트결제는 100만원 초과된 금액은 지원하지 않습니다. 무통장 결제를 선택해주세요.");
                return;
            }
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
            if(isEmpty($("#cash-name")[0].value)) {
                alert("입금자명을 입력해주세요.");
                return;
            }
            $("#order-form").submit();
        }
    });
});