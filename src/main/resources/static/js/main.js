var mainSlide = $("div.slide-wrapper")[0];
var mainSlideLength = $("div.slide-wrapper").children().length;
var firstMainContent = mainSlide.firstElementChild;
var lastMainContent = mainSlide.lastElementChild;
var mainContentWidth = 1300;
var mainSlideSpeed = 800;
var mainSlideIndex = 1;

var beltSlide = $("div.belt-slide-wrapper")[0];
var beltSlideLength = $("div.belt-slide-wrapper").children().length;
var firstBeltContent = beltSlide.firstElementChild;
var lastBeltContent = beltSlide.lastElementChild;
var beltContentWidth = 1300;
var beltSlideSpeed = 400;
var beltSlideIndex = 1;

////////////////////////////메인슬라이드
mainSlide.insertBefore(lastMainContent.cloneNode(true), firstMainContent);
mainSlide.append(firstMainContent.cloneNode(true));

mainSlide.style.width = (1300*5)+"px";
mainSlide.style.transform = "translate("+(-mainContentWidth)+"px, 0)";

automatedMainSlide();

$(document).on("click", "button.left", function() {
    pushRightMain();
});

$(document).on("click", "button.right", function() {
    pushLeftMain();
});

//메인슬라이드 페이징
$(document).on("click", ".page-btn", function(e){
    let pageNum = e.target.dataset.value;

    mainSlideIndex = pageNum - 1;
    pushLeftMain();
});

//오른쪽버튼
function pushLeftMain() {
    mainSlideIndex++;
    let mainSlideDelta = (mainContentWidth * -1 * mainSlideIndex) + "px";
    $("div.slide-wrapper")[0].style.transition = mainSlideSpeed+"ms";
    $("div.slide-wrapper")[0].style.transform = "translate("+mainSlideDelta+", 0)";
    if(mainSlideIndex > mainSlideLength) {
        setTimeout(execute, mainSlideSpeed);
        mainSlideIndex = 1;
        let mainSlideDelta = (mainContentWidth * -1 * mainSlideIndex) + "px";
        function execute() {
            $("div.slide-wrapper")[0].style.transition = "0ms";
            $("div.slide-wrapper")[0].style.transform = "translate("+mainSlideDelta+", 0)";
        }
    }

    $(".page-btn").removeClass("selected");
    $(".page-btn")[mainSlideIndex-1].classList.add("selected");
}

//왼쪽버튼
function pushRightMain() {
    mainSlideIndex--;
    let mainSlideDelta = (mainContentWidth * -1 * mainSlideIndex) + "px";
    $("div.slide-wrapper")[0].style.transition = mainSlideSpeed+"ms";
    $("div.slide-wrapper")[0].style.transform = "translate("+mainSlideDelta+", 0)";
    if(mainSlideIndex == 0) {
        setTimeout(execute, mainSlideSpeed);
        mainSlideIndex = 3;
        let mainSlideDelta = (mainContentWidth * -1 * mainSlideIndex) + "px";
        function execute() {
            $("div.slide-wrapper")[0].style.transition = "0ms";
            $("div.slide-wrapper")[0].style.transform = "translate("+mainSlideDelta+", 0)";
        }
    }
}

function automatedMainSlide() {
    setInterval(pushLeftMain, 6000);
}

/////////////////////////////벨트 슬라이드
beltSlide.insertBefore(lastBeltContent.cloneNode(true), firstBeltContent);
beltSlide.append(firstBeltContent.cloneNode(true));

beltSlide.style.width = (1300*5)+"px";
beltSlide.style.transform = "translate("+(-beltContentWidth)+"px, 0)";

automatedBeltSlide();

$(document).on("click", "button.left", function() {
    pushRightBelt();
});

$(document).on("click", "button.right", function() {
    pushLeftBelt();
});

//오른쪽버튼
function pushLeftBelt() {
    beltSlideIndex++;
    let beltSlideDelta = (beltContentWidth * -1 * beltSlideIndex) + "px";
    $("div.belt-slide-wrapper")[0].style.transition = beltSlideSpeed+"ms";
    $("div.belt-slide-wrapper")[0].style.transform = "translate("+beltSlideDelta+", 0)";
    if(beltSlideIndex > beltSlideLength) {
        setTimeout(execute, beltSlideSpeed);
        beltSlideIndex = 1;
        let beltSlideDelta = (beltContentWidth * -1 * beltSlideIndex) + "px";
        function execute() {
            $("div.belt-slide-wrapper")[0].style.transition = "0ms";
            $("div.belt-slide-wrapper")[0].style.transform = "translate("+beltSlideDelta+", 0)";
        }
    }
}

//왼쪽버튼
function pushRightBelt() {
    beltSlideIndex--;
    let beltSlideDelta = (beltContentWidth * -1 * beltSlideIndex) + "px";
    $("div.belt-slide-wrapper")[0].style.transition = beltSlideSpeed+"ms";
    $("div.belt-slide-wrapper")[0].style.transform = "translate("+beltSlideDelta+", 0)";
    if(beltSlideIndex == 0) {
        setTimeout(execute, beltSlideSpeed);
        beltSlideIndex = 3;
        let beltSlideDelta = (beltContentWidth * -1 * beltSlideIndex) + "px";
        function execute() {
            $("div.belt-slide-wrapper")[0].style.transition = "0ms";
            $("div.belt-slide-wrapper")[0].style.transform = "translate("+beltSlideDelta+", 0)";
        }
    }
}

function automatedBeltSlide() {
    setInterval(pushLeftBelt, 4500);
}