$(document).ready(function() {
    $(document).on("click", "#testbtn", function(e) {
        window.open("/popup", "child1", "width=500, height=500");
    });
});
