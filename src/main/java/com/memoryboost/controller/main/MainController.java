package com.memoryboost.controller.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(HttpServletRequest request, Model model){

        return "main";
    }





}
