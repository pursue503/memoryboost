package com.memoryboost.controller.main;

import com.memoryboost.domain.dto.product.request.ProductSaveRequestDTO;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @GetMapping("/")
    public String mainPage(HttpServletRequest request, Model model){

        return "main";
    }

    @GetMapping("/search-preview/{keyword}")
    @ResponseBody
    public ArrayList<String> searchPreview(@PathVariable("keyword") String keyword) {
        return productRepository.productSearch(keyword);
    }




}
