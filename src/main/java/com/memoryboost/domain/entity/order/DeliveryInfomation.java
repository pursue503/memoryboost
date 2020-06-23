package com.memoryboost.domain.entity.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_delivery_infomation")
@Entity
public class DeliveryInfomation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diNo;

    @ManyToOne
    @JoinColumn(name = "ORDER_NO" , nullable = false)
    private Order order;

    // 받는사람
    @Column(nullable = false)
    private String diRecipients;

    // 핸드폰번호
    @Column(nullable = false)
    private String diTel;

    // 우편번호
    @Column(nullable = false)
    private String diZipCode;

    // 주소
    @Column(nullable = false)
    private String diAddress;

    // 상세주소
    @Column(nullable = false)
    private String diDetailAddress;

    // 코멘트
    @Column
    private String diComment;

    @Builder
    public DeliveryInfomation(Order order, String diRecipients, String diTel,
                              String diZipCode, String diAddress, String diDetailAddress, String diComment) {
        this.order = order;
        this.diRecipients = diRecipients;
        this.diTel = diTel;
        this.diZipCode = diZipCode;
        this.diAddress = diAddress;
        this.diDetailAddress = diDetailAddress;
        this.diComment = diComment;
    }
}