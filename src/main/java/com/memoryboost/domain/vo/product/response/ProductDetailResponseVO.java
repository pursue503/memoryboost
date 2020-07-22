package com.memoryboost.domain.vo.product.response;

import com.memoryboost.domain.dto.product.response.ProductDetailReviewResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
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

    private Long grade1,grade2,grade3,grade4,grade5;

    public void starListSetting(List<ProductDetailReviewStarResponseVO> starList){

        List<Integer> reviewGradeList = new ArrayList<>();
        if(starList.size() < 5) {
            for(ProductDetailReviewStarResponseVO star : starList) {
                reviewGradeList.add(star.getReviewGrade());
            }
            for(int i=5; i>=1; i--) {
                if(!reviewGradeList.contains(i)) {
                    starList.add(new ProductDetailReviewStarResponseVO(i,0L));
                }
            }

        }

        for(ProductDetailReviewStarResponseVO star : starList) {
            switch (star.getReviewGrade()) {
                case 1:
                    this.grade1 = star.getGradeCount();
                    break;
                case 2:
                    this.grade2 = star.getGradeCount();
                    break;
                case 3 :
                    this.grade3 = star.getGradeCount();
                    break;
                case 4:
                    this.grade4 = star.getGradeCount();
                    break;
                case 5:
                    this.grade5 = star.getGradeCount();
                    break;

            }
        }

    }

}
