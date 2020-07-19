package com.memoryboost.domain.entity.enquiry;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_reply")
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNo;

    @ManyToOne
    @JoinColumn(name = "ENQUIRY_NO", nullable = false)
    private Enquiry enquiry;

    // 작성자
    @Column(nullable = false)
    private String replyer;

    // 내용
    @Column(nullable = false)
    private String replyContent;

    // 작성일자
    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date replyDate;

    @Builder
    public Reply(Enquiry enquiry, String replyer, String replyContent) {
        this.enquiry = enquiry;
        this.replyer = replyer;
        this.replyContent = replyContent;
    }
}
