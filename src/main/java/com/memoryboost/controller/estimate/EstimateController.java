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
}
