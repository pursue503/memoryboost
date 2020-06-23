package com.memoryboost.controller.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(HttpServletRequest request){

        String path = request.getSession().getServletContext().getRealPath("\\");

        log.info(path);

        return "main";
    }

}
