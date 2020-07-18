var temp = 0;

$(document).on("keyup", ".test", function(e) {
    if(e.which == 38 || e.which == 40) {
        if(e.which == 38) {
            if(temp > 0) {
                temp--;
            }
        } else {
            temp++;
        }
    }
    console.dir(temp);
});