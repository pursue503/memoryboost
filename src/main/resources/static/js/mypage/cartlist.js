$(document).ready(function() {
    let totaldiv = $("div.total-price");
    let finaldiv = $("div.final-price");
    let shipmentdiv = $("div.shipment-fee");
    var totalPrice = 0;
    var finalPrice = 0;
    var shipmentFee = 0;

    var chkbox = $("input.chkbox");
    //총 금액 표시(체크 될때마다만)
    $(document).on("change", "input.chkbox", function(e) {
        for(let a of chkbox) {
            if(a.checked){
                let form = $(".form-amount."+a.dataset.productnum)[0];
                let productPrice = Number(form[0].dataset.price);
                let productAmount = Number(form[0].dataset.prev);
                totalPrice += (productPrice * productAmount);
            }
        }
        if(!totalPrice) {
            shipmentFee = 0;
        } else {
            shipmentFee = 2500;
        }
        totaldiv[0].innerHTML = totalPrice.toLocaleString()+"원";
        shipmentdiv[0].innerHTML = shipmentFee.toLocaleString()+"원";
        finalPrice = totalPrice + shipmentFee;
        finaldiv[0].innerHTML = finalPrice.toLocaleString()+"원";
        totalPrice = 0;
    });

    //전체 선택,해제
    $(document).on("click", "div.control > .btn", function(e) {
        let status = e.target.classList[1];

        if(status == "all") {
            for(let a of chkbox) {
                if(!a.checked) {
                    a.click();
                }
            }
        } else {
            for(let a of chkbox) {
                if(a.checked) {
                    a.click();
                }
            }
        }
    });

    //주문하기
    $(document).on("click", ".btn.order", function(e) {
        e.preventDefault();
        let chkbox = $("input.chkbox");
        let count = 0;
        let cartList = new Array();

        for(let a of chkbox) {
            if(a.checked) {
                $("div.hidden-value").append("<input type='hidden' name='cartList' value="+a.value+" />");
                count++;
            }
        }

        if(!count) {
            alert("주문할 상품을 선택해 주세요.");
        } else {
            $("#form-submit").submit();
        }
    })
});