$(document).ready(function() {
    var temp = document.getElementsByClassName("title");
    shave(temp, 50);

    setWriteButton();
});

function setWriteButton() {
    var boardName = getBoardName();
    var arg = "location.href = '/community/write?board="+boardName+"';"
    var arg2 = "location.href = '/notification';"

    if(boardName == "notification") {
        $("button.write-noti").attr("onclick", arg2);
    } else {
        $("button.write").attr("onclick", arg);
    }
}