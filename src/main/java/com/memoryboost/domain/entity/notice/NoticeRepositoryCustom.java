package com.memoryboost.domain.entity.notice;

import com.memoryboost.domain.dto.main.NoticeMainPageResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticeListResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticePrevNextResponseDTO;
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

    //메인페이지 공지사항 , 이벤트
    List<NoticeMainPageResponseDTO> mainPageNoticeAndEventList(int category);

    //이전 다음
    NoticePrevNextResponseDTO noticePrevAndNext(Long noticeNo, int category);

}
