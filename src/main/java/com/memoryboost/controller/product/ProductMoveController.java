package com.memoryboost.controller.product;

import com.memoryboost.service.paging.PagingService;
import com.memoryboost.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductMoveController {

    private final ProductService productService;
    private final PagingService pagingService;

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword,
                         @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
                         @RequestParam(value = "page",required = false,defaultValue = "1") int page , Model model) {

        if(keyword.trim().equals("")) {
            model.addAttribute("product",null);
        } else {
            model.addAttribute("product", productService.productSearch(keyword,order,page));
            model.addAttribute("paging",pagingService.searchPaging(keyword,page));
        }
        return "search/search";
    }

}
