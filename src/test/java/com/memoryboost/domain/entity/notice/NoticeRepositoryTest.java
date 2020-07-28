package com.memoryboost.domain.entity.notice;

import com.memoryboost.domain.dto.notice.response.NoticeListResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void test(){
        log.info("로컬테스트.");
    }


//    @Test
//    public void noticeAllList() {
//
//        List<NoticeListResponseDTO> noticeListResponseDTOS = noticeRepository.findByNoticeAll(0,1);
//
//        noticeListResponseDTOS.forEach(notice -> log.info(notice.toString()));
//
//    }
//
//    @Test
//    public void noticeOneData(){
//        NoticeResponseDTO noticeResponseDTO = noticeRepository.findByNoticeNo(1L);
//
//        log.info(noticeResponseDTO.toString());
//
//    }

}
