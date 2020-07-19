package com.memoryboost.domain.entity.estimate;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_estimate_detail")
@Entity
public class EstimateDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateDetailNo;

    @ManyToOne
    @JoinColumn(name = "ESTIMATE_NO", nullable = false)
    private Estimate estimate;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO" , nullable = false)
    private Product product;

    @Builder
    public EstimateDetail(Estimate estimate, Product product) {
        this.estimate = estimate;
        this.product = product;
    }
}
