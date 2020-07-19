package com.memoryboost.domain.entity.product;

import com.memoryboost.domain.dto.product.request.ProductFilterSearchRequestDTO;
import com.memoryboost.domain.dto.product.response.ProductSearchResponseDTO;
import com.memoryboost.domain.entity.product.detail.vga.Vga;
import com.memoryboost.domain.entity.product.detail.vga.VgaRepository;
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

    @Autowired
    private VgaRepository vgaRepository;

    @Before
    public void saveProduct() {
        Product product = productRepository.save(Product.builder().productName("갤럭시 GALAX 지포스 RTX 2070 SUPER EX OC D6 8GB PINK Edition").
                productCategory(1)
                .productThumbnail("주소~")
                .productDescription("설명")
                .productPrice(600000)
                .build());
        vgaRepository.save(Vga.builder().productNo(product).vgaCompany("갤럭시").vgaChipset("2070SUPER").vgaSeries("RTX").build());
    }

    @After
    public void deleteProduct() {
        vgaRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void searchTest(){

        String[] arr = {"2070"};
        List<ProductSearchResponseDTO> productSearchResponseDTOList = productRepository.productSearch(arr,"priceDesc", 1);

        for (ProductSearchResponseDTO s : productSearchResponseDTOList) {
            log.info(s.toString());
        }

        ProductSearchResponseDTO productSearchResponseDTO = productSearchResponseDTOList.get(0);

        assertThat(productSearchResponseDTO.getProductName()).contains(arr[0]);
    }

    @Test
    public void searchPagingTotalCountTest(){

        String[] searchArr = {"갤럭시"};

        int result = productRepository.countByProductNameContaining(searchArr);

        assertThat(result).isEqualTo(1);

    }

    @Test
    public void searchPreviewTest() {

        String keyword = "갤럭시";

        ArrayList<String> keywordArr = productRepository.searchPreview(keyword);

        for (String s : keywordArr) {
            log.info(s);
        }
    }

    @Test
    public void filterSearchTest(){


        ProductFilterSearchRequestDTO filterDTO = new ProductFilterSearchRequestDTO();
        filterDTO.setCategory("vga");
        filterDTO.setSelect1("갤럭시");
        filterDTO.setSelect2("2070SUPER");
        filterDTO.setSelect3("RTX");

        List<ProductSearchResponseDTO> searchResponseDTO = productRepository.productFilterSearch(filterDTO,"priceDesc",1);

        assertThat(filterDTO.getSelect1()).contains(searchResponseDTO.get(0).getProductName());

    }

}
