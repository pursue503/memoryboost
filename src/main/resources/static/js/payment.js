$(document).ready(function() {
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

        $("input#"+classname)[0].checked = true;
    });
});