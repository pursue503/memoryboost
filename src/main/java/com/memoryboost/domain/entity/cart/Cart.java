package com.memoryboost.domain.entity.cart;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.product.Product;
import com.memoryboost.domain.vo.cart.request.CartProductCountUpdateRequestVO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_cart")
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartNo;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID",nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO",nullable = false)
    private Product product;

    @Column
    private int productCnt;

    @Builder
    public Cart(Member member, Product product, int productCnt) {
        this.member = member;
        this.product = product;
        this.productCnt = productCnt;
    }

    public void cartProductCountUpdate(CartProductCountUpdateRequestVO updateRequestVO) {
        this.productCnt = updateRequestVO.getProductCnt();
    }

}
