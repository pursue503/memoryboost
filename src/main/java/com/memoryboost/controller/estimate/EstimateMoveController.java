package com.memoryboost.controller.estimate;

import com.memoryboost.domain.dto.product.request.ProductFilterSearchRequestDTO;
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
public class EstimateMoveController {

    private final ProductService productService;
    private final PagingService pagingService;

    @GetMapping("/estimate")
    public String estimate(ProductFilterSearchRequestDTO filterDTO,
                               @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
                               @RequestParam(value = "page",required = false,defaultValue = "1") int page,Model model) {
        if(filterDTO == null || filterDTO.getCategory() == null) {
            filterDTO.setCategory("cpu");
        }
        model.addAttribute("category", "cpu");
        model.addAttribute("product",productService.filterSearch(filterDTO,order,page));
        model.addAttribute("paging",pagingService.filterSearchPaging(filterDTO, page));
        return "/estimate";
    }

    @GetMapping("/estimate-fragment-result")
    public String estimateFragmentResult(ProductFilterSearchRequestDTO filterDTO,
                               @RequestParam(value = "keyword",required = false) String keyword,
                               @RequestParam(value = "category", required = false, defaultValue = "cpu") String category,
                               @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
                               @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                               Model model) {
        model.addAttribute("category", category);
        model.addAttribute("product",productService.estimateFilterSearch(filterDTO,order,page,keyword));
        model.addAttribute("paging",pagingService.estimateFilterSearchPaging(filterDTO,keyword, page));
        return "estimate :: result";
    }

    @GetMapping("/filter-change")
    public String filterChange(@RequestParam(value = "category") String category, Model model) {
        model.addAttribute("category", category);
        return "search/search :: filter-"+category;
    }

    @GetMapping("/add-fragment")
    public String addFragment(@RequestParam(value = "productNo") Long productNo, @RequestParam(value = "category") String category, Model model) {
        model.addAttribute("category", category);
        model.addAttribute("product",productService.productDetail(productNo));
        return "layout/estimate-layout :: added";
    }



}
