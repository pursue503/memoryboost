package com.memoryboost.domain.entity.product.detail.desktop;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_desktop")
@Entity
public class Desktop {

    // 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long desktop;

    // 데탑정보 // name = 여기테이블에 지정할 컬럼이름
    @ManyToOne
    @JoinColumn(name = "fk1_product_no",nullable = false)
    private Product fk1ProductNo;

    // 부품
    @ManyToOne
    @JoinColumn(name = "fk2_product_no", nullable = false)
    private Product fk2ProductNo;

    @Builder
    public Desktop(Product fk1ProductNo, Product fk2ProductNo) {
        this.fk1ProductNo = fk1ProductNo;
        this.fk2ProductNo = fk2ProductNo;
    }
}
