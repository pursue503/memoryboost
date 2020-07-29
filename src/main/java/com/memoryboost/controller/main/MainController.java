package com.memoryboost.controller.main;

import com.memoryboost.service.main.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String mainPage(HttpServletRequest request, Model model){

        model.addAttribute("notice",mainService.noticeMainPage());
        model.addAttribute("event",mainService.eventMainPage());
        model.addAttribute("newProduct"  , mainService.newProduct());
        model.addAttribute("post", mainService.newPostReview());
        model.addAttribute("product",mainService.mainProductRandom());
        return "main";
    }

    @GetMapping("/community")
    public String community(){
        return "community";
    }


}
