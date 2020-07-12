package com.memoryboost.domain.entity.product.detail.keyboard;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_keyboard")
@Entity
public class Keyboard {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyboardNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no",nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String keyboardCompany;

    //연결방식 유무선
    @Column
    private String keyboardConnection;

    // 접점방식 ex ) 기계식 멤브레인 등
    @Column
    private String keyboardContact;

    @Builder
    public Keyboard(Product productNo, String keyboardCompany, String keyboardConnection, String keyboardContact) {
        this.productNo = productNo;
        this.keyboardCompany = keyboardCompany;
        this.keyboardConnection = keyboardConnection;
        this.keyboardContact = keyboardContact;
    }
}
