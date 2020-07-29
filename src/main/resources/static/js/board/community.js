$(document).ready(function() {
    var temp = document.getElementsByClassName("title");
    shave(temp, 50);

    $(document).on("click", "button.write-noti", function() {
        location.href = "/notification";
    });

    /*setWriteButton();*/
});

//글쓰기 버튼 방향
/*function setWriteButton() {
    var boardName = getBoardName();
    var arg = "location.href = '/community/write?board="+boardName+"';"
    var arg2 = "location.href = '/notification';"

    if(boardName == "notification") {
        $("button.write-noti").attr("onclick", arg2);
    } else {
        $("button.write").attr("onclick", arg);
    }
}*/

//게시판 이름 점등
function lightNavigator() {
    var boardName = getParam("board");

    var navigator = $("nav.community > button");

    navigator.removeClass("selected");
    $("nav.community > button."+boardName).addClass("selected");
}