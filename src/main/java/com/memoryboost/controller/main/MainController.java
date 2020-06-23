package com.memoryboost.controller.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;

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

        String path = "/home/ec2-user/upload/name";

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

        String txt = "안녕하세요";
        String filePath = "/home/ec2-user/upload/tetet.txt";

        try{
            File file = new File(filePath);

            FileWriter fw = new FileWriter(file,true);
            fw.write(txt);
            fw.flush();
            fw.close();
        } catch (Exception e ) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

}
