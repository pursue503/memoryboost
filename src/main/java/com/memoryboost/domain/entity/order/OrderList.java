package com.memoryboost.domain.entity.order;

import com.memoryboost.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_order_list")
@Entity
public class OrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderListNo;

    //주문번호
    @ManyToOne
    @JoinColumn(name = "ORDER_NO" , nullable = false)
    private Order order;
    
    //상품번호
    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO" ,nullable = false)
    private Product product;

    //상품개수
    @Column(nullable = false)
    private int productCnt;

    @Builder
    public OrderList(Order order, Product product, int productCnt) {
        this.order = order;
        this.product = product;
        this.productCnt = productCnt;
    }
}
