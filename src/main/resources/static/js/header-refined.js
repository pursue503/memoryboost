var keywordSelected = null;

//검색어 입력시
$(document).on("keyup", "input#keyword", function(e) {
    if(!isEmpty(e.target.value)) {
        if(e.which == 38 || e.which == 40) {
            e.preventDefault();
            let recommendsLength = $(".recommends").length;
            if(recommendsLength > 0) { //검색 결과가 있을때만
                $(".recommends").removeClass("keywordSelected");
                if(e.which == 38) { //위
                    setIndex(1, 0, 4, false);
                } else { //아래
                    setIndex(1, 0, 4, true);
                }
                $(".recommends")[keywordSelected].classList.add("keywordSelected");
                $("input#keyword")[0].value = $(".recommends")[keywordSelected].innerHTML;
            }
        } else {
            keywordSelected = null;
            $.ajax({
                type : "GET",
                url : "/search-preview/"+e.target.value
            })
            .done(function(response) {
                if(response.length > 0) {
                    $("div.funcs").addClass("ontype");
                    $("div.search").addClass("recommend");
                    let tag = "<ul>";
                    for(let index in response) {
                        if(index < 5) {
                            let refined = String(response[index]).replace("/"+e.target.value+"/i", "asdasd");
                            tag += "<li class='recommends'>"+response[index]+"</li>";
                        }
                    }
                    tag += "</ul>";
                    $("div.keyword-list")[0].innerHTML = tag;
                } else {
                    $("div.funcs").removeClass("ontype");
                    $("div.search").removeClass("recommend");
                    $("div.keyword-list").empty();
                }
            })
            .fail(function(response) {
            })
        }
    } else {
        $("div.funcs").removeClass("ontype");
        $("div.search").removeClass("recommend");
    }
});

//연관 검색어 hover
$(document).on("click", ".recommends", function(e) {
    $("input#keyword")[0].value = e.target.innerHTML;
    setTimeout(execute, 50);
    function execute() {
        $("form#form-keyword").submit();
    }
});

//인덱스 설정
var setIndex = function(step, start, end, status) { //(변화량, 범위시작, 범위끝, 증감(true:증가, false:감소))
    if(isEmpty(keywordSelected)) {
        console.dir("초기화")
        keywordSelected = start;
        console.dir(keywordSelected);
        return;
    } else {
        if(status && keywordSelected < end) {
            console.dir("더함")
            keywordSelected += step;
        } else if(!status && keywordSelected > start) {
            keywordSelected -= step;
            console.dir("뺌")
        }
        console.dir(keywordSelected);
        return;
    }
}

//검색어 지우기
$(document).on("click", ".btn.reset", function() {
    $("div.search").removeClass("recommend");
    $("div.funcs").removeClass("ontype");
    $("div.keyword-list").empty();
});