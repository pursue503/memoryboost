package com.memoryboost.domain.entity.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_notice")
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeNo;

    // 분류
    @Column(nullable = false)
    private String noticeCategory;

    // 제목
    @Column(nullable = false)
    private String noticeTitle;

    // 내용
    @Column(nullable = false)
    private String noticeConten;

    // 작성일자
    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date noticeDate;

    @Builder
    public Notice(String noticeCategory, String noticeTitle, String noticeConten) {
        this.noticeCategory = noticeCategory;
        this.noticeTitle = noticeTitle;
        this.noticeConten = noticeConten;
    }
}
