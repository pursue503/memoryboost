package com.memoryboost.domain.entity.product.detail.vga;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_vga")
@Entity
public class Vga {

    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vgaNo;

    // 상품번호
    @ManyToOne
    @JoinColumn(name = "product_no",nullable = false)
    private Product productNo;

    // 제조회사 // 이엠택 갤럭시
    @Column(nullable = false)
    private String vgaCompany;

    // 칩셋 ex) AMD,등
    @Column
    private String vgaChipset;

    // 제품 시리즈 //ex) rtx 2070
    @Column
    private String vgaSeries;

    @Builder
    public Vga(Product productNo, String vgaCompany, String vgaChipset, String vgaSeries) {
        this.productNo = productNo;
        this.vgaCompany = vgaCompany;
        this.vgaChipset = vgaChipset;
        this.vgaSeries = vgaSeries;
    }
}
