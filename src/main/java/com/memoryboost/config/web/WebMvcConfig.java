package com.memoryboost.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/notice/**")
                .addResourceLocations("file:/home/ec2-user/notice/");
        //S3 상품이미지 경로 숨기기 /product/**
        ///product/로 시작된 경로 요청이들어오면 아래 경로로 변환
        // ex) /product/detail/abc.jpg -> s3서버주소의 /product/detail/abc1234.jpg
        // s3 경로를 product까지 써줘야함.
        registry.addResourceHandler("/product/**")
                .addResourceLocations("https://memoryboost-product-images.s3.ap-northeast-2.amazonaws.com/product/");

    }
}
