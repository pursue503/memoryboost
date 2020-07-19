package com.memoryboost.domain.dto.product.request;

import com.memoryboost.domain.entity.product.detail.cpu.QCpu;
import com.querydsl.core.BooleanBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

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
