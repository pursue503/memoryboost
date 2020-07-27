package com.memoryboost.controller.main;

import com.memoryboost.domain.dto.order.request.OrderSingleProductSaveRequestDTO;
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

    @GetMapping("/test")
    public String test(OrderSingleProductSaveRequestDTO order){
        log.info(order.toString());

        return "test";
    }




}
