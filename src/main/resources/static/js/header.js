$(document).ready(function() {
    //스크롤 액션
    const elem = $(document)[0];
    var prevScroll = 0;
    var prevHeaderOffset = 0;
    var headerHeight = 110;
    var keywordSelected = null;

    elem.addEventListener("scroll", function(e) {
        //현재 스크롤 위치
        let currentScroll = document.documentElement.scrollTop;
        //스크롤 변화량(+/- 구분됨)
        let scrollDelta = currentScroll - prevScroll;
        //현재 헤더 위치
        let currentHeaderOffset = Number($("header").css("top").replace("px", ""));

        if(currentScroll > prevScroll) {
            if(currentScroll < headerHeight) {
                $("header").css("top", -(currentScroll));
            } else {
                $("header").addClass("mini");
                $("header.mini").css("top", 0);
            }
        } else {
            if(currentScroll < headerHeight) {
                $("header").css("top", -(currentScroll));
                $("header").removeClass("mini");
            } else {
                $("header.mini").css("top", 0);
            }
        }
        setPrevScroll(currentScroll);
    });

    //이전 스크롤 위치 저장
    var setPrevScroll = function(y) {
        prevScroll = y;
    };

    //검색창 포커스 아웃 시
    $(document).on("blur", "input#search-input", function(e) {
        setTimeout(execute, 100);
        function execute() {
            $("div.search-div").removeClass("typing"); //타이핑 상태 off
            $("div.auto-keyword").empty(); //자동완성 검색어 리스트 삭제
        }
    });

    //검색어 입력시
    $(document).on("keyup", "input#search-input", function(e) {
        let keyword = e.target.value;

        if(!isEmpty(keyword)) { //검색어가 비어있지 않음
            $("div.auto-keyword").empty(); //자동완성 검색어 리스트 삭제
            $("div.search-div").addClass("typing"); //타이핑 상태 on

            //자동완성 검색어 ajax
            $.ajax({
                type : "GET",
                url : "/search-preview/"+e.target.value
            })
            .done(function(response) {
                if(response.length > 0) { //연관검색어 있음
                    for(let index in response) { //검색어 리스트 루프
                        let tag = "<div class='keyword-list'>"+response[index]+"</div>";
                        $("div.auto-keyword").append(tag);
                        if(index == 5) { //5개까지만 표현
                            break;
                        }
                    }
                } else { //연관검색어 없음
                    let tag = "연관 검색어가 없습니다";
                    $("div.auto-keyword").append(tag);
                }
            })
            .fail(function(response) {
                console.dir("통신실패[헤더:자동완성검색어]");
            })
        } else { //검색어 비어있음
            $("div.search-div").removeClass("typing"); //타이핑 상태 off
        }
    });

    //검색어 리셋
    $(document).on("click", "button#search-reset", function(e) {
        $("div.search-div").removeClass("typing");
        $("div.auto-keyword").empty(); //자동완성 검색어 리스트 삭제
    });

    //연관 검색어 선택 시
    $(document).on("click", "div.keyword-list", function(e) {
        console.dir("asdasd");
        $("input#search-input")[0].value = e.target.innerHTML;
        setTimeout(execute, 50);
        function execute() {
            $("form#header-search-form").submit();
        }
    });
});