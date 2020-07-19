package com.memoryboost.domain.dto.product.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFilterSearchRequestDTO {

    //table 구분
    private String category;

    //1번부터 회사,분류,분류,분류
    private String select1;
    private String select2;
    private String select3;

}
