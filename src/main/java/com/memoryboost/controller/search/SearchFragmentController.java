package com.memoryboost.controller.search;


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
public class SearchFragmentController {

    private final ProductService productService;
    private final PagingService pagingService;

    @GetMapping("/search-fragment")
    public String search(@RequestParam("keyword") String keyword,
                         @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
                         @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                         @RequestParam(value = "layout", required = false, defaultValue = "list") String layout, Model model ){

        model.addAttribute("keyword", keyword);
        model.addAttribute("layout", layout);
        model.addAttribute("product", productService.productSearch(keyword,order,page));
        model.addAttribute("paging",pagingService.searchPaging(keyword,page));
        return "search/search :: fragment-result";
    }


    @GetMapping("/search-filter")
    public String searchFilter(ProductFilterSearchRequestDTO filterDTO,
                               @RequestParam(value = "category") String category,
                               @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
                               @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                               @RequestParam(value = "layout", required = false, defaultValue = "list") String layout, Model model) {
        model.addAttribute("category", category);
        model.addAttribute("product",productService.filterSearch(filterDTO,order,page));
        model.addAttribute("paging",pagingService.filterSearchPaging(filterDTO, page));
        model.addAttribute("layout",layout);
        return "search/search :: fragment-result";
    }

}
