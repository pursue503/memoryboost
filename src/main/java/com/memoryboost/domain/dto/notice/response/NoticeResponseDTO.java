package com.memoryboost.domain.dto.notice.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NoticeResponseDTO {

    private Long noticeNo;
    private int noticeCategory;
    private String noticeTitle;
    private String noticeContent;
    private Date noticeDate;

    private List<String> noticeImagePath;

}
