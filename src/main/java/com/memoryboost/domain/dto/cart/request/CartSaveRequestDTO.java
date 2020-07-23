package com.memoryboost.domain.dto.cart.request;

import com.memoryboost.domain.entity.cart.Cart;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartSaveRequestDTO {

    private Long productNo;
    private int productCnt;

    public Cart toEntity(Product product, Member member) {
        return Cart.builder().member(member).product(product).productCnt(this.productCnt).build();
    }

}
