package com.memoryboost.controller.product;

import com.memoryboost.domain.vo.product.response.ProductDetailResponseVO;
import com.memoryboost.service.paging.PagingService;
import com.memoryboost.service.product.ProductService;
import com.memoryboost.service.review.ReviewService;
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
    private final ReviewService reviewService;
    private final PagingService pagingService;


    @GetMapping("/product-detail/{productNo}")
    public String productDetail(@PathVariable("productNo") Long productNo,Model model,
                                @RequestParam(value = "order", required = false, defaultValue = "0") int order,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        try{
            model.addAttribute("product",productService.productDetail(productNo));
            model.addAttribute("review",reviewService.productDetailReview(productNo,order,page));
            model.addAttribute("paging",pagingService.productDetailReviewPaging(productNo,page));
            return "product/product-detail";
        } catch (NullPointerException e) {
            return "error";
        }
    }

}
