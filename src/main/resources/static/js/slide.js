var panel = null; //슬라이드 패널(통)
var slideLength = null; //슬라이드 갯수 3
var firstContent = null; //첫번째 슬라이드
var lastContent = null; //마지막 슬라이드
var contentWidth = null; //슬라이드 길이
var slideSpeed = null //슬라이드 속도
var slideIndex = 1; //슬라이드 인덱스(시작은 1, 양 끝에 하나씩 추가 했으니까)

var slide = { panel : null,
              slideLength : 0,
              firstContent : null,
              lastContent : null,
              contentWidth : 0,
              slideSpeed : 0,
              slideIndex : 1 };

function slideSetting(element, num, num) {
    setPanel(element);
    setSlideLength(element);
    setContents();
    setContentWidth(num);
    setSlideSpeed(num);
}

//setPanel($("div.b"));
function setPanel(element) {
    panel = element[0];
};

//setSlideLength($("div.b"));
function setSlideLength(element) {
    slideLength = element.children().length;
}

//setContents(slide);
function setContents() {
    firstContent = slide.firstElementChild;
    lastContent = slide.lastElementChild;
}

function setContentWidth(num) {
    contentWidth = num;
}

function setSlideSpeed(num) {
    slideSpeed = num;
}

//function setSlideLength(panel) {
//}
//function setslideLength()

/*
$(document).ready(function() {
    //양 끝에 첫,마지막 슬라이드 복사해서 추가.
    panel.insertBefore(lastContent.cloneNode(true), firstContent);
    panel.append(firstContent.cloneNode(true));

    $("div.a")[0].style.width = contentWidth + "px"; //슬라이트 패널 길이 = 컨텐츠 하나 길이 (overflow: hidden);
    $("div.b")[0].style.transform = "translate("+(-contentWidth)+"px, 0)"; //두번째(=원래는 첫번째) 슬라이드로 이동

    //왼쪽버튼
    $(document).on("click", "button.right", function(e) {
        slideIndex++; //인덱스 +1
        let slideDelta = contentWidth * -1 * slideIndex;
        $("div.b")[0].style.transition = slideSpeed+"ms";
        $("div.b")[0].style.transform = "translate("+slideDelta+"px, 0)";
        if(slideIndex == slideLength) {
            console.dir("asd");
            setTimeout(execute, slideSpeed);

            function execute() {
                $("div.b")[0].style.transition = 0+"ms";
                $("div.b")[0].style.transform = "translate(0, 0)";
            }
            slideIndex = 0;
        }
    });

    //오른쪽버튼
    $(document).on("click", "button.left", function(e) {
        slideIndex--;
        let slideDelta = contentWidth * -1 * slideIndex;
        $("div.b")[0].style.transition = slideSpeed+"ms";
        $("div.b")[0].style.transform = "translate("+slideDelta+"px, 0)";
        if(slideIndex == 0) {
            console.dir("돌아가!");
            setTimeout(execute, slideSpeed);

            function execute() {
                console.dir("예!");
                $("div.b")[0].style.transition = 0+"ms";
                $("div.b")[0].style.transform = "translate(-900px, 0)";
            }
            slideIndex = 3;
            console.dir(slideIndex);
        }
    });
});

//왼쪽버튼
function pushRight() {
    slideIndex++; //인덱스 +1
    let slideDelta = contentWidth * -1 * slideIndex;
    $("div.b")[0].style.transition = slideSpeed+"ms";
    $("div.b")[0].style.transform = "translate("+slideDelta+"px, 0)";
    if(slideIndex == slideLength) {
        console.dir("asd");
        setTimeout(execute, slideSpeed);

        function execute() {
            $("div.b")[0].style.transition = 0+"ms";
            $("div.b")[0].style.transform = "translate(0, 0)";
        }
        slideIndex = 0;
    }
};

//오른쪽버튼
function pushLeft() {
    slideIndex--;
    let slideDelta = contentWidth * -1 * slideIndex;
    $("div.b")[0].style.transition = slideSpeed+"ms";
    $("div.b")[0].style.transform = "translate("+slideDelta+"px, 0)";
    if(slideIndex == 0) {
        setTimeout(execute, slideSpeed);

        function execute() {
            $("div.b")[0].style.transition = 0+"ms";
            $("div.b")[0].style.transform = "translate(-900px, 0)";
        }
        slideIndex = 3;
        console.dir(slideIndex);
    }
};*/
