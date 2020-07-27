$(document).ready(function() {
    let slide = $("div.b")[0]; //슬라이드 패널(통)
    let slideLength = $("div.b").children().length; //슬라이드 갯수 3
    let firstContent = slide.firstElementChild; //첫번째 슬라이드
    let lastContent = slide.lastElementChild; //마지막 슬라이드
    let contentWidth = 300; //슬라이드 길이
    let slideSpeed = 300; //슬라이드 속도
    let slideIndex = 1; //슬라이드 인덱스(시작은 1, 양 끝에 하나씩 추가 했으니까)

    //양 끝에 첫,마지막 슬라이드 복사해서 추가.
    slide.insertBefore(lastContent.cloneNode(true), firstContent);
    slide.append(firstContent.cloneNode(true));

    $("div.a")[0].style.width = contentWidth + "px"; //슬라이트 패널 길이 = 컨텐츠 하나 길이 (overflow: hidden);
    $("div.b")[0].style.transform = "translate("+(-contentWidth)+"px, 0)"; //두번째(=원래는 첫번째) 슬라이드로 이동

    //오른쪽버튼
    $(document).on("click", "button.right", function(e) {
        pushLeft();
    });

    //왼쪽버튼
    $(document).on("click", "button.left", function(e) {
        pushRight();
    });

    automatedSlide();

    function automatedSlide() {
        setInterval(pushLeft, 5000);
    }

    //오른쪽버튼
    function pushLeft() {
        slideIndex++;
        let slideDelta = contentWidth * -1 * slideIndex;
        $("div.b")[0].style.transition = slideSpeed+"ms";
        $("div.b")[0].style.transform = "translate("+slideDelta+"px, 0)";
        if(slideIndex > slideLength) {
            setTimeout(execute, slideSpeed);
            slideIndex = 1;
            function execute() {
                $("div.b")[0].style.transition = 0+"ms";
                $("div.b")[0].style.transform = "translate("+(-contentWidth) * slideIndex+"px, 0)";
            }
        }
    }

    //왼쪽버튼
    function pushRight() {
        slideIndex--;
        let slideDelta = contentWidth * -1 * slideIndex;
        $("div.b")[0].style.transition = slideSpeed+"ms";
        $("div.b")[0].style.transform = "translate("+slideDelta+"px, 0)";
        if(slideIndex == 0) {
            setTimeout(execute, slideSpeed);
            slideIndex = 3;
            function execute() {
                $("div.b")[0].style.transition = 0+"ms";
                $("div.b")[0].style.transform = "translate("+(-contentWidth) * slideIndex+"px, 0)";
            }
        }
    }
});