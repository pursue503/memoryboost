package com.memoryboost.domain.entity.notice;

import com.memoryboost.domain.dto.notice.request.NoticeUpdateRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

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
    private int noticeCategory;

    // 제목
    @Column(nullable = false)
    private String noticeTitle;

    // 내용
    @Column(nullable = false)
    private String noticeContent;

    // 작성일자
    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date noticeDate;

//    @Temporal(value = TemporalType.DATE)
    @UpdateTimestamp
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false)
    private Date noticeModifiedDate;

    @Builder
    public Notice(int noticeCategory, String noticeTitle, String noticeContent) {
        this.noticeCategory = noticeCategory;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
    }

    public void noticeUpdate(NoticeUpdateRequestDTO noticeUpdateRequestDTO) {
        this.noticeTitle = noticeUpdateRequestDTO.getNoticeTitle();
        this.noticeContent = noticeUpdateRequestDTO.getNoticeContent();
    }

}
