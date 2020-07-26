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
            log.info("null");
            filterDTO.setCategory("cpu");
        }
        model.addAttribute("product",productService.filterSearch(filterDTO,order,page));
        model.addAttribute("paging",pagingService.filterSearchPaging(filterDTO, page));
        return "redirect:/";
    }

    @GetMapping("/estimate-fragment-result")
    public String estimateFragmentResult(ProductFilterSearchRequestDTO filterDTO,
                               @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
                               @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                               Model model) {
        model.addAttribute("product",productService.filterSearch(filterDTO,order,page));
        model.addAttribute("paging",pagingService.filterSearchPaging(filterDTO, page));
        return "";
    }

}
