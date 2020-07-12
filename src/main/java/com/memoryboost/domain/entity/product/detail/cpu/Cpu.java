package com.memoryboost.domain.entity.product.detail.cpu;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_cpu")
@Entity
public class Cpu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpuNo;


    @ManyToOne
    @JoinColumn(name = "product_no",nullable = false)
    private Product productNo;

    //제조회사
    @Column(nullable = false)
    private String cpuCompany;

    //세대 ex) 9세대 10세대 8세대
    @Column
    private String cpuGeneration;

    //모델명 ex) i3 i5 i9
    @Column
    private String cpuModel;

    @Builder
    public Cpu(Product productNo, String cpuCompany, String cpuGeneration, String cpuModel) {
        this.productNo = productNo;
        this.cpuCompany = cpuCompany;
        this.cpuGeneration = cpuGeneration;
        this.cpuModel = cpuModel;
    }
}
