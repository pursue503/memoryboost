package com.memoryboost.domain.dto.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class NoticeMainPageResponseDTO {

    private Long noticeNo;
    private int noticeCategory;
    private String noticeTitle;
    private Date noticeDate;

}
