package com.memoryboost.controller.main;

import com.memoryboost.domain.dto.product.request.ProductSaveRequestDTO;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.product.ProductRepository;
import com.memoryboost.domain.vo.member.MemberOAuth2VO;
import com.memoryboost.service.paging.PagingService;
import com.memoryboost.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {


    private final ProductService productService;
    private final PagingService pagingService;

    @GetMapping("/")
    public String mainPage(HttpServletRequest request, Model model){

        return "main";
    }

    @GetMapping("/search-preview/{keyword}")
    @ResponseBody
    public ArrayList<String> searchPreview(@PathVariable("keyword") String keyword) {
        return productService.productSearchPreview(keyword);
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword,
                         @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
                         @RequestParam(value = "page",required = false,defaultValue = "1") int page , Model model ){

        if(keyword.trim().equals("")) {
            model.addAttribute("product",null);
        } else {
            model.addAttribute("keyword", keyword);
            model.addAttribute("layout","list");
            model.addAttribute("product", productService.productSearch(keyword,order,page));
            model.addAttribute("paging",pagingService.searchPaging(keyword,page));
        }
        return "search/search";
    }

}
