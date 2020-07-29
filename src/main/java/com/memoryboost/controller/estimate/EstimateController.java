package com.memoryboost.controller.estimate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class EstimateController {

    @GetMapping("/templates")
    public String templatePage() { return "basic"; }

    @GetMapping("/mypage/inquiry")
    public String inquiryPage() { return "mypage/inquiry"; }

    @GetMapping("/popup")
    public String popupTestPage() { return "popup"; }

    @GetMapping("/writereview")
    public String writeReviewPage() { return "mypage/writereview"; }

    @GetMapping("/estimatetest")
    public String estimateTestPage() { return "/estimate"; }

    @GetMapping("/boardtest")
    public String boardTestPage() { return "board/board"; }

    @GetMapping("/community/estimate")
    public String estimateBoardPage() { return "board/estimate-com"; }

    @GetMapping("/community/review")
    public String reviewBoardPage() { return "board/review-com"; }

    @GetMapping("/community/notification")
    public String notificationBoardPage() { return "board/notification-com"; }

    @GetMapping("/community/update")
    public String updateBoardPage() { return "board/update-com"; }

    @GetMapping("/community/event")
    public String eventBoardPage() { return "board/event-com"; }

    @GetMapping("/write")
    public String writeBoardPage() { return "board/write"; }

    @GetMapping("/write-notification")
    public String writeNotificationPage() { return "board/write-notification"; }
}
