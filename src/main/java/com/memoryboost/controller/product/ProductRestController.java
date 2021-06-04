package com.memoryboost.controller.product;

import com.memoryboost.domain.dto.product.request.ProductFilterSearchRequestDTO;
import com.memoryboost.service.paging.PagingService;
import com.memoryboost.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductService productService;
    //페이징
    private final PagingService pagingService;

//    //검색 비동기
    @GetMapping("/search-asyn")
    public Map<String, Object> searchAsyn(@RequestParam("keyword") String keyword,
                                          @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
                                          @RequestParam(value = "page",required = false,defaultValue = "1") int page,
                                          @RequestParam(value = "layout", required = false, defaultValue = "list")  String layout){
        Map<String,Object> resultMap = new HashMap<>();

        if(keyword.trim().equals("")) {
            resultMap.put("product", null);
            return resultMap;
        }
        resultMap.put("product",productService.productSearch(keyword,order,page));
        resultMap.put("paging", pagingService.searchPaging(keyword,page));
        resultMap.put("layout", layout);
        return resultMap;
    }





    //타임리프 fragment 기능으로 대체됨.
//    @GetMapping("/search-filter")
//    public Map<String,Object> searchFilter(ProductFilterSearchRequestDTO filterDTO,
//                                           @RequestParam(value = "order", required = false, defaultValue = "popular") String order,
//                                           @RequestParam(value = "page",required = false,defaultValue = "1") int page,
//                                           @RequestParam(value = "layout", required = false, defaultValue = "list") String layout) {
//
//        Map<String,Object> resultMap = new HashMap<>();
//        resultMap.put("product",productService.filterSearch(filterDTO,order,page));
//        resultMap.put("paging",pagingService.filterSearchPaging(filterDTO, page));
//        resultMap.put("layout",layout);
//        return resultMap;
//    }


}
