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
    $("div.flow")[0].className = "flow "+e.target.classList[2];
});