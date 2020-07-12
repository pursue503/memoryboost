package com.memoryboost.domain.entity.product.detail.motherboard;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_motherboard")
@Entity
public class Motherboard {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long motherboardNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no",nullable = false)
    private Product productNo;

    // 제조회사
    @Column(nullable = false)
    private String motherboardCompany;

    // 소켓 인텔 1151-v2
    @Column
    private String motherboardSocket;

    //칩셋 종류 z370  h310 등
    @Column
    private String motherboardChipset;

    @Builder
    public Motherboard(Product productNo, String motherboardCompany, String motherboardSocket, String motherboardChipset) {
        this.productNo = productNo;
        this.motherboardCompany = motherboardCompany;
        this.motherboardSocket = motherboardSocket;
        this.motherboardChipset = motherboardChipset;
    }
}
