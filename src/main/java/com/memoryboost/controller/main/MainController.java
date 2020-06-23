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
    public String test(){

        String path = "/home/ec-user/upload/name";

        File folder = new File(path);

        if(!folder.exists()) {
            try{
                folder.mkdirs();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";

    }

    @GetMapping("/test2")
    public String test2(){
        String path = "\\home\\ec-user\\upload\\name";
        File folder = new File(path);
        if(!folder.exists()) {
            try{
                folder.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }

    @GetMapping("/test3")
    public String test3(){
        String path = "/upload2";

        File folder = new File(path);

        if(!folder.exists()) {
            try{
                folder.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/";

    }
    @GetMapping("/test4")
    public String test4(){
        String path = "/upload2";

        File folder = new File(path);

        if(!folder.exists()) {
            try{
                folder.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "redirect:/";

    }

}
