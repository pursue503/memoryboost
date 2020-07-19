package com.memoryboost.controller.product;

import com.memoryboost.service.paging.PagingService;
import com.memoryboost.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductMoveController {

    private final ProductService productService;
    private final PagingService pagingService;


    @GetMapping("/product/detail/{productNo}")
    public String productDetail(@PathVariable("productNo") Long productNo, Model model) {
        try{
            model.addAttribute("product",productService.productDetail(productNo));
            return "페이지";
        } catch (NullPointerException e) {
            return "error";
        }


    }

}
