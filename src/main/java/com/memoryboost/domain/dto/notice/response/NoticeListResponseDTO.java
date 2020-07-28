package com.memoryboost.domain.dto.notice.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeListResponseDTO {

    private int NoticeNumber;
    private Long noticeNo;
    private int noticeCategory;
    private String noticeTitle;
    private Date noticeDate;


}
