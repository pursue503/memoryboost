package com.memoryboost.controller.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(HttpServletRequest request, Model model){

        ServletContext path = request.getSession().getServletContext();

        model.addAttribute("path",path);

        return "main";
    }

    @GetMapping("/test")
    public void test(){

        String path = "/home/ec-user/upload/name";

        File folder = new File(path);

        if(!folder.exists()) {
            try{
                folder.mkdir();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @GetMapping("/test2")
    public void test2(){
        String path = "\\home\\ec-user\\upload\\name";
        File folder = new File(path);
        if(!folder.exists()) {
            try{
                folder.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
