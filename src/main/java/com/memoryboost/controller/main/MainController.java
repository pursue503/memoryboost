package com.memoryboost.controller.main;

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
@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(HttpServletRequest request, Model model){

        return "main";
    }

    @GetMapping("/test1")
    public String test(){
        return "test";
    }

    @PostMapping("/test")
    public String ec2Test(@RequestParam("fileTest")List<MultipartFile> multipartFileList) throws IOException {

        for(MultipartFile multipartFile : multipartFileList) {
            log.info("size" + multipartFile.getSize());
            File file = new File("/home/ec2-user/notice/" + multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
        }
        return "redirect:/";
    }


}
