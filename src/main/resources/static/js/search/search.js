var keywordPreview = function(elem, search) { //(element, input)
    ///search-preview/{keyword}
    let keyword = elem.value;

    let tag = "<ul>";
    if(!isEmpty(keyword)) {
        $.ajax({
            type : "GET",
            url : "/search-preview/"+keyword,
        })
        .done(function(response) {
            if(response.length > 0) {
                $(".search-preview").addClass("pop");
                for(let obj of response) {
                    tag += "<li>"+obj+"</li>";
                }
                tag += "</ul>";
            } else {
                tag = "";
            }
            $(".search-preview")[0].innerHTML = tag;
        })
        .fail(function(response) {
            console.dir("통신 실패[keywordPreview]");
        });
    } else {
        $(".search-preview").removeClass("pop");
        $(".search-preview > ul").empty();
    }
};

//추천검색어 눌렀을때 검색 실행.
var pushKeyword = function(keyword, tagTo) { //(검색어, 넣을 태그)
    tagTo.value = keyword;

    setTimeout(execute, 400);

    function execute() {
        $("form#form-search").submit();
    }
};