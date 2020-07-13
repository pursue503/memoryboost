var selected;
//카테고리 클릭
$(document).on("click", "button.btn.category", function(e) {
    $(".image-added").remove();
    $("form#product-form")[0].reset();
    let categoryNum = e.target.value;
    let categoryName = e.target.innerText;
    selected = parseInt(categoryNum - 1);
    $("input.productCategory")[0].value = categoryName;
    $("input#productCategory")[0].value = categoryNum;
    let basicInfo = $("table#basic-info")[0];
    let detailInfo = $("table#detail-info")[0];
    $("table#detail-info").empty();
    let tag = "";

    //상세 스펙 입력 구성 변경
    switch(categoryNum) {
        case "1" : //CPU
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                   +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='cpuCompany' type='text' name='cpuCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>세대</td>"
                       +"<td><input id='cpuGeneration' type='text' name='cpuGeneration' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>모델명</td>"
                       +"<td><input id='cpuModel' type='text' name='cpuModel' /></td>"
                   +"</tr>";
            break;
        case "2" : //MOTHERBOARD
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                   +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='motherboardCompany' type='text' name='motherboardCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>소켓</td>"
                       +"<td><input id='motherboardSocket' type='text' name='motherboardSocket' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>칩셋종류</td>"
                       +"<td><input id='motherboardChipset' type='text' name='motherboardChipset' /></td>"
                   +"</tr>";
            break;
        case "3" : //GPU
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                       +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='vgaCompany' type='text' name='vgaCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>칩셋</td>"
                       +"<td><input id='vgaChipset' type='text' name='vgaChipset' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>제품시리즈</td>"
                       +"<td><input id='vgaSeries' type='text' name='vgaSeries' /></td>"
                   +"</tr>";
            break;
        case "4" : //MEMORY
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                       +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='memoryCompany' type='text' name='memoryCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>제품용량</td>"
                       +"<td><input id='memorySize' type='text' name='memorySize' /></td>"
                   +"</tr>";
            break;
        case "5" : //HDD
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                       +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='hddCompany' type='text' name='hddCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>HDD용량</td>"
                       +"<td><input id='hddSize' type='text' name='hddSize' /></td>"
                   +"</tr>";
            break;
        case "6" : //SSD
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                       +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='ssdCompany' type='text' name='ssdCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>SSD용량</td>"
                       +"<td><input id='ssdSize' type='text' name='ssdSize' /></td>"
                   +"</tr>";
            break;
        case "7" : //CASE
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                       +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='caseCompany' type='text' name='caseCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>규격</td>"
                       +"<td><input id='caseSize' type='text' name='caseSize' /></td>"
                   +"</tr>";
            break;
        case "8" : //POWER
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                       +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='powerCompany' type='text' name='powerCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>정격출력</td>"
                       +"<td><input id='powerWatt' type='text' name='powerWatt' /></td>"
                   +"</tr>";
            break;
        case "9" : //KEYBOARD
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                       +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='keyboardCompany' type='text' name='keyboardCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>연결방식</td>"
                       +"<td><input id='keyboardConnection' type='text' name='keyboardConnection' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>접촉방식</td>"
                       +"<td><input id='keyboardContact' type='text' name='keyboardContact' /></td>"
                   +"</tr>";
            break;
        case "10" : //MOUSE
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                       +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='mouseCompany' type='text' name='mouseCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>연결방식</td>"
                       +"<td><input id='mouseConnection' type='text' name='mouseConnection' /></td>"
                   +"</tr>";
            break;
        case "11" : //MONITOR
            tag =  "<tr><th colspan='2'>상세정보</th></tr>"
                       +"<tr>"
                       +"<td class='title'>제조회사</td>"
                       +"<td><input id='monitorCompany' type='text' name='monitorCompany' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>패널</td>"
                       +"<td><input id='monitorPanel' type='text' name='monitorPanel' /></td>"
                   +"</tr>"
                   +"<tr>"
                       +"<td class='title'>사이즈</td>"
                       +"<td><input id='monitorSize' type='text' name='monitorSize' /></td>"
                   +"</tr>";
            break;
    }
    $("table#detail-info").append(tag);
    $("input#productName").focus();
});

//파일 업로드(file) 행동 대체
$(document).on("click", "button.btn.file", function(e) {
    e.preventDefault();
    let thisClassName = e.target.classList[2];
    $("input."+thisClassName).click();
    //document.getElementById("productThumbnail").click();
});

$(document).on("change", "input[type=file]", function(e) {
    let thisClassName = e.target.classList[0];
    let imagePath = e.target.value;
    let imageName = imagePath.split("\\");
    $("input.path."+thisClassName)[0].value = imageName[imageName.length-1];
});

//상세이미지 추가
$(document).on("click", "button.btn.addImage", function(e) {
    e.preventDefault();
    let detailImageList = $("input.productDetailImage");
    let numbering = (detailImageList.length+1);
    let tagInTable = "<tr class='image-added'>"
                   +"<td class='title'>상세이미지"+numbering+"</td>"
                   +"<td class='disabled'><input class='path productDetailImage"+numbering+"' type='text' disabled /><button class='btn file productDetailImage"+numbering+"'>파일 선택</button></td>"
               +"</tr>";
    let tagOutTable = "<input class='productDetailImage"+numbering+" productDetailImage image-added' type='file' name='detailImages' />";
    $("table#basic-info").append(tagInTable);
    $("div.div-hide").append(tagOutTable);
});

//엔터키 등록
$(document).on("keydown", "input[type=text]", function(e){
    if(e.keyCode == 13) {
        e.preventDefault();
        $("button#btn-submit").click();
    }
});

//등록버튼
$(document).on("click", "button#btn-submit", function(e) {
    e.preventDefault();
    var form = $("form#product-form")[0];
    var params = new FormData(form);

    $.ajax({
        type : "POST",
        enctype: 'multipart/form-data',
        url : "/admin/product/upload",
        data : params,
        processData: false,
        contentType: false
    })
    .done(function(response) {
        if(response.result) { //상품 등록 성공
            /*$("form#product-form")[0].reset();
            $("input#productName")[0].focus();*/
            $(".btn.category")[selected].click();
            $("span.msg.success").toggleClass("pop");
            setTimeout(execute, 300);
            function execute() {
                $("span.msg.success").toggleClass("pop");
            }
        } else { //상품 등록 실패
            $("span.msg.fail").toggleClass("pop");
            setTimeout(execute, 300);
            function execute() {
                $("span.msg.fail").toggleClass("pop");
            }
        }
    })
    .fail(function(response) {
        console.dir("통신실패");
        $("span.msg.fail").toggleClass("pop");
        setTimeout(execute, 300);
        function execute() {
            $("span.msg.fail").toggleClass("pop");
        }
    });
});

//단축키(입력창 변경)
$(document).on("keydown", function(e) {
    if(e.altKey && e.which == 49) {
        $(".btn.category")[0].click();
    } else if(e.altKey && e.which == 50) {
        $(".btn.category")[1].click();
    } else if(e.altKey && e.which == 51) {
         $(".btn.category")[2].click();
    } else if(e.altKey && e.which == 52) {
         $(".btn.category")[3].click();
    } else if(e.altKey && e.which == 53) {
         $(".btn.category")[4].click();
    } else if(e.altKey && e.which == 54) {
         $(".btn.category")[5].click();
    } else if(e.altKey && e.which == 55) {
         $(".btn.category")[6].click();
    } else if(e.altKey && e.which == 56) {
         $(".btn.category")[7].click();
    } else if(e.altKey && e.which == 57) {
         $(".btn.category")[8].click();
    } else if(e.altKey && e.which == 48) {
         $(".btn.category")[9].click();
    } else if(e.altKey && e.which == 189) {
         $(".btn.category")[10].click();
    }
});