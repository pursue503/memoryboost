package com.memoryboost.domain.entity.refund;

import com.memoryboost.domain.entity.order.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_refund")
@Entity
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refundNo;

    @ManyToOne
    @JoinColumn(name = "ORDER_NO",nullable = false)
    private Order order;

    //환불날짜
    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date refundDate;

    //주문날짜
    @Temporal(value = TemporalType.DATE)
    @Column
    private Date refundCompleteDate;

    @Column
    private String refundReason;

    @Builder
    public Refund(Order order, Date refundCompleteDate, String refundReason) {
        this.order = order;
        this.refundCompleteDate = refundCompleteDate;
        this.refundReason = refundReason;
    }
}
