package com.memoryboost.controller.admin;

import com.memoryboost.util.product.ProductS3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final ProductS3Uploader productS3Uploader;

    @GetMapping("/product/upload-page")
    public String uploadPage(){
        log.info(productS3Uploader.toString());
        log.info(productS3Uploader.getBucket());
        log.info(productS3Uploader.getAmazonS3Client().getRegion().toString());
        log.info(productS3Uploader.getAmazonS3Client().toString());

        return "productInsert";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("data")MultipartFile multipartFile) throws IOException {
        log.info(multipartFile.getOriginalFilename());
        String url = productS3Uploader.upload(multipartFile,"product");

        return url;
    }

}
