package com.memoryboost.controller.estimate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class EstimateController {
    @GetMapping("/estimate")
    public String estimatePage() {
        return "estimate";
    }

    @GetMapping("/templates")
    public String templatePage() { return "basic"; }

    @GetMapping("/paytest")
    public String payPage() { return "payment"; }

    @GetMapping("/mypage/myinfo")
    public String myinfoPage() { return "mypage/myinfo"; }

    @GetMapping("/mypage/cartlist")
    public String cartlistPage() { return "mypage/cartlist"; }

    @GetMapping("/mypage/inquiry")
    public String inquiryPage() { return "mypage/inquiry"; }

    @GetMapping("/mypage/orderlist")
    public String orderlistPage() { return "mypage/orderlist"; }
}
