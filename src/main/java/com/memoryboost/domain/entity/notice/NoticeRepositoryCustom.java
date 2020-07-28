package com.memoryboost.domain.entity.notice;

import com.memoryboost.domain.dto.notice.response.NoticeListResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticeResponseDTO;

import java.util.List;

public interface NoticeRepositoryCustom {

    List<NoticeListResponseDTO> findByNoticeAll(int category, int page);

    int countByNoticeAll(int category);

    //1개찾기
    NoticeResponseDTO findByNoticeNo(Long noticeNo);

    //삭제
    //공지 사항 이미지 정보 가져 오기.
    List<NoticeImage> findByNoticeImage(Notice notice);

}
