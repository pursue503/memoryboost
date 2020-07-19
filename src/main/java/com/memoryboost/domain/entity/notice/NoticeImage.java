package com.memoryboost.domain.entity.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_notice_image")
@Entity
public class NoticeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeImageNo;

    @ManyToOne
    @JoinColumn(name = "NOTICE_NO",nullable = false)
    private Notice notice;

    @Column(nullable = false)
    private String noticeImagePath;

    @Builder
    public NoticeImage(Notice notice, String noticeImagePath) {
        this.notice = notice;
        this.noticeImagePath = noticeImagePath;
    }
}
