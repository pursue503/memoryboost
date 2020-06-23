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

    //코어형태
    @Column
    private String cpuCore;

    //쓰레드 형태
    @Column
    private String cpuThread;

    @Builder
    public Cpu(Product productNo, String cpuCompany, String cpuCore, String cpuThread) {
        this.productNo = productNo;
        this.cpuCompany = cpuCompany;
        this.cpuCore = cpuCore;
        this.cpuThread = cpuThread;
    }
}
