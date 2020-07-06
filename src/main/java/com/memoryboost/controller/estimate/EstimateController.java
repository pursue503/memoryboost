package com.memoryboost.controller.estimate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class EstimateController {
    @GetMapping("estimate")
    public String estimatePage() {
        return "estimate";
    }
}
