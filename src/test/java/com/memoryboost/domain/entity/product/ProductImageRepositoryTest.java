package com.memoryboost.domain.entity.product;

import com.memoryboost.domain.vo.product.response.ProductDetailResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductImageRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Before
    public void saveProduct() {
        Product product = productRepository.save(Product.builder().productName("갤럭시 GALAX 지포스 RTX 2070 SUPER EX OC D6 8GB PINK Edition").
                productCategory(1)
                .productThumbnail("주소~")
                .productDescription("설명")
                .productPrice(600000)
                .build());

        for(int i=0; i<5; i++) {
            productImageRepository.save(ProductImage.builder().productNo(product).productImagePath(i + " 번째").build());
        }

    }

    @After
    public void deleteProduct() {
        productImageRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void productDetailTest() {

        Optional<Product> product = productRepository.findById(1L);
        Product productEntity = product.get();

        ProductDetailResponseVO productDetailResponseVO = productRepository.productDetail(productEntity);

        log.info(productDetailResponseVO.toString());
    }

}
