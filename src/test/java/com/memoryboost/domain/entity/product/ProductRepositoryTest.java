package com.memoryboost.domain.entity.product;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void productInsertTest(){

        productRepository.save(Product.builder().
                productName("갤럭시 GALAX 지포스 RTX 2070 SUPER EX OC D6 8GB PINK Edition").
                productCategory(3).
                productThumbnail("https://pursue503-amazon-webservice-repository.s3.ap-northeast-2.amazonaws.com/product/2070+%ED%95%91%ED%81%AC.jpg").
                productDescription("RTX 2070 SUPER / 12nm / 1605MHz , 부스트 1815MHz / 2560개 / PCIe3.0x16 / GDDR6(DDR6) / 14000MHz / 8GB / 256-bit / HDMI / DP / 최대 모니터 4개 / 최대 215W / 정격파워 650W 이상 / 전원부: 7+2페이즈 / 2개 팬 / 285mm / 백플레이트 / LED 라이트").
                productPrice(617300).build());
    }

    @Test
    public void productFindByNameTest(){

        productRepository.save(Product.builder().
                productName("갤럭시 GALAX 지포스 RTX 2070 SUPER EX OC D6 8GB PINK Edition").
                productCategory(3).
                productThumbnail("https://pursue503-amazon-webservice-repository.s3.ap-northeast-2.amazonaws.com/product/2070+%ED%95%91%ED%81%AC.jpg").
                productDescription("RTX 2070 SUPER / 12nm / 1605MHz , 부스트 1815MHz / 2560개 / PCIe3.0x16 / GDDR6(DDR6) / 14000MHz / 8GB / 256-bit / HDMI / DP / 최대 모니터 4개 / 최대 215W / 정격파워 650W 이상 / 전원부: 7+2페이즈 / 2개 팬 / 285mm / 백플레이트 / LED 라이트").
                productPrice(617300).build());


//        String productName = "2070";
//        productRepository.findByProductName(productName).forEach(product -> log.info(product.getProductName()));

        List<Product> productList = productRepository.findByProductNameLike("%2070%");

        if(productList.isEmpty()) {
            log.info("비었습니다.");
        }
        for (Product p : productList) {
            log.info(p.getProductName());
        }


    }

}
