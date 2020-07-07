$(document).ready(function() {
    //전체노드 탐색 후 liftup 효과 적용
    var elems = document.body.getElementsByTagName("*");
    for(a of elems) {
        if(a.classList.contains("liftup")) {
            a.classList.add("is-open");
        }
    }
});