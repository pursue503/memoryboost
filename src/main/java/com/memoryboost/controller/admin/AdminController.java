package com.memoryboost.controller.admin;

import com.memoryboost.domain.dto.product.request.ProductDetailSaveRequestDTO;
import com.memoryboost.domain.dto.product.request.ProductSaveRequestDTO;
import com.memoryboost.service.admin.AdminService;
import com.memoryboost.util.product.ProductS3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/**")
@RestController
public class AdminController {

    private final AdminService adminService;

//    @PostMapping("/product/upload")
//    @ResponseBody
//    public Map<String, Boolean> productUpload(ProductSaveRequestDTO productSaveRequestDTO, @RequestParam("thumbnail") MultipartFile thumbnailFile,
//                                              @RequestParam("detailImages") List<MultipartFile> detailImageList,
//                                              ProductDetailSaveRequestDTO productDetailSaveRequestDTO){
//        Map<String,Boolean> resultMap = new HashMap<>();
//        resultMap.put("result",adminService.productUpload(productSaveRequestDTO,thumbnailFile,detailImageList,productDetailSaveRequestDTO));
//        return resultMap;
//    }

}
