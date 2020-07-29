package com.memoryboost.domain.dto.notice.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeUpdateRequestDTO {

    private Long noticeNo;
    private String noticeTitle;
    private String noticeContent;

}
