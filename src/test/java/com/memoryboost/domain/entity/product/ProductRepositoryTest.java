package com.memoryboost.domain.entity.product;

import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.querydsl.core.QueryResults;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

//    @Before
//    public void saveProduct() {
//        productRepository.save(Product.builder().productName("갤럭시 GALAX 지포스 RTX 2070 SUPER EX OC D6 8GB PINK Edition").
//                productCategory(1)
//                .productThumbnail("주소~")
//                .productDescription("설명")
//                .productPrice(600000)
//                .build());
//    }
//
//    @After
//    public void deleteProduct() {
//        productRepository.deleteAll();
//    }

    @Test
    public void searchTest(){

        String[] arr = {"2070"};
        List<ProductSearchResponseDTO> productSearchResponseDTOList = productRepository.findByProductSearch(arr);

        ProductSearchResponseDTO productSearchResponseDTO = productSearchResponseDTOList.get(0);

        assertThat(productSearchResponseDTO.getProductName()).contains(arr[0]);

    }

    @Test
    public void searchPreviewTest() {

        String keyword = "갤럭시";

        ArrayList<String> keywordArr = productRepository.productSearch(keyword);

        for (String s : keywordArr) {
            log.info(s);
        }
    }

}
