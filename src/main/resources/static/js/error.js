$(document).ready(function() {
    var intervalId = setInterval(countdown, 1000);
    var start = 4;

    function countdown() {
        var count = $("#count")[0];
        if(!start) {
            clearInterval(intervalId);
            location.replace("/");
        }
        count.innerHTML = (start--);
    }

    $(document).on("click", "img#logo", function() {
        location.replace("/");
    });
});