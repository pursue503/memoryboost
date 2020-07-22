package com.memoryboost.domain.vo.product.response;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductDetailResponseVO {

    private Long productNo;
    private String productName;
    private Integer productCategory;
    private String productDescription;
    private String productThumbnail;
    private Integer productPrice;
    private List<String> productImagePath;

    private double gradeAvg;

    private List<ProductDetailReviewStarResponseVO> starList;

    public void starListSetting(){

        int size = starList.size();
        List<Integer> reviewGradeList = new ArrayList<>();
        if(size < 5) {
            for(ProductDetailReviewStarResponseVO star : starList) {
                reviewGradeList.add(star.getReviewGrade());
            }
        }

        for(int i=5; i>=1; i--) {
            if(!reviewGradeList.contains(i)) {
                starList.add(new ProductDetailReviewStarResponseVO(i,0L));
            }
        }

    }

}
