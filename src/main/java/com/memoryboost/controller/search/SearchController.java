package com.memoryboost.controller.search;

import com.memoryboost.domain.dto.product.request.ProductFilterSearchRequestDTO;
import com.memoryboost.service.paging.PagingService;
import com.memoryboost.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SearchController {

    private final ProductService productService;
    private final PagingService pagingService;

    @GetMapping("/search-preview/{keyword}")
    @ResponseBody
    public ArrayList<String> searchPreview(@PathVariable("keyword") String keyword) {
        return productService.productSearchPreview(keyword);
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword,
                         @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
                         @RequestParam(value = "page",required = false,defaultValue = "1") int page , Model model ){

        model.addAttribute("keyword", keyword);
        model.addAttribute("layout","list");
        model.addAttribute("product", productService.productSearch(keyword,order,page));
        model.addAttribute("paging",pagingService.searchPaging(keyword,page));
        return "search/search";
    }

}
