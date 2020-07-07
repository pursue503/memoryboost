package com.memoryboost.controller.main;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/")
    public String mainPage(HttpServletRequest request, Model model){

        return "main";
    }




}
