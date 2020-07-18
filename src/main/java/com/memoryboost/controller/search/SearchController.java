package com.memoryboost.controller.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SearchController {
    @GetMapping("/search")
    public String searchPage() { return "search/search"; }
}
