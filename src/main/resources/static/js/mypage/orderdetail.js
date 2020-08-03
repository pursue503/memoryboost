$(document).ready(function() {
    var popX = window.outerWidth/2 - 225;
    var popY = window.outerHeight/2 - 225;
    //리뷰작성 버튼
    $(document).on("click", ".btn.review", function(e) {
        let productNo = e.target.dataset.productnum;
        let productName = e.target.dataset.productname;
        let property = "width=450, height=450";
        let a ='status=no, height=' + 450  + ', width=' + 450  + ', left='+ popX + ', top='+ popY
        window.open("/writereview?productNo="+productNo+"&productName="+productName, 'review', a);
    })

    $(document).on("click", "#submit-button", function(e) {
        e.preventDefault();
        let submitFlag = true;
        for(let obj of $(".required")) {
            if(isEmpty(obj.value)) {
                submitFlag = false;
                break;
            }
        }

        if(submitFlag) {
            alert("배송정보가 수정됐습니다.")
            $("#addr-form").submit();
        } else {
            alert("배송정보를 모두 입력해주세요.")
        }
    });
});