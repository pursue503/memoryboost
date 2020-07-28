package com.memoryboost.domain.dto.notice.request;

import com.memoryboost.domain.entity.notice.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeSaveRequestDTO {

    private int noticeCategory;
    private String noticeTitle;
    private String noticeContent;

    public Notice toEntity() {
        return Notice.builder().noticeCategory(noticeCategory).noticeTitle(noticeTitle).noticeContent(noticeContent).build();
    }

}
