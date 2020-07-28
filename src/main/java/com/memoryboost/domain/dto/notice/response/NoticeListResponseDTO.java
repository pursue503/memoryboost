package com.memoryboost.domain.dto.notice.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class NoticeListResponseDTO {

    private int NoticeNumber;
    private Long noticeNo;
    private int noticeCategory;
    private String noticeTitle;
    private Date noticeDate;


}
