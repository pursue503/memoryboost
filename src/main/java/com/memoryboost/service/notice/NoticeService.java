package com.memoryboost.service.notice;

import com.memoryboost.domain.dto.notice.request.NoticeSaveRequestDTO;
import com.memoryboost.domain.dto.notice.request.NoticeUpdateRequestDTO;
import com.memoryboost.domain.dto.notice.response.NoticeListResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticePrevNextResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticeResponseDTO;
import com.memoryboost.domain.entity.notice.Notice;
import com.memoryboost.domain.entity.notice.NoticeImage;
import com.memoryboost.domain.entity.notice.NoticeImageRepository;
import com.memoryboost.domain.entity.notice.NoticeRepository;
import com.memoryboost.util.product.ProductS3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeImageRepository noticeImageRepository;
    //파일이름 변경용
    private final ProductS3Uploader productS3Uploader;
    
    private static final String path = "/home/ec2-user/notice/";
    private static final String dbPath = "/notice/";

    @Transactional(readOnly = true)
    public List<NoticeListResponseDTO> noticeListResponseDTOList(int category, int page) {

        List<NoticeListResponseDTO> noticeListResponseDTOList = noticeRepository.findByNoticeAll(category,page);

        int count = noticeRepository.countByNoticeAll(category) - (page * 10 - 10);

        for(NoticeListResponseDTO notice : noticeListResponseDTOList) {
            notice.setNoticeNumber(count);
            count--;
        }
        return noticeListResponseDTOList;
    }

    @Transactional
    public boolean noticeSave(NoticeSaveRequestDTO noticeSaveRequestDTO , List<String> pathList) throws IOException {

        if(noticeSaveRequestDTO.patternCheck()) { // true == 공백 또는 null
            return false;
        }

        Notice notice = noticeRepository.save(noticeSaveRequestDTO.toEntity());
        if(pathList != null) {
            for(String filePath : pathList) {
                String realPath = path + filePath;
                noticeImageRepository.save(NoticeImage.builder().notice(notice).noticeImagePath(filePath).noticeRealImagePath(realPath + filePath).build());
            }
        }


        return true;
    }

    @Transactional
    public boolean noticeDelete(Long noticeNo) {

        Notice notice = noticeRepository.findById(noticeNo).orElseThrow(NullPointerException::new);

        List<NoticeImage> noticeImageList = noticeRepository.findByNoticeImage(notice);

        for(NoticeImage noticeImage : noticeImageList) {
            File file = new File(noticeImage.getNoticeRealImagePath());
            if(file.exists()) { // 파일 존재여부 확인.
                file.delete(); //파일제
                noticeImageRepository.delete(noticeImage); // 데이터삭제.
            }
        }
        noticeRepository.delete(notice);
        return true;
    }

    @Transactional(readOnly = true)
    public NoticeResponseDTO noticeDetail(Long noticeNo) {
        return noticeRepository.findByNoticeNo(noticeNo);
    }

    //파일 저장
    public String noticeFileTempSave(MultipartFile multipartFile) {

        //확장자 얻기
        String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        //파일 이름 랜덤 생성.
        String saveFileName = UUID.randomUUID() + extension;

        File file = new File(path + saveFileName);

        try{
            multipartFile.transferTo(file);
            return dbPath + saveFileName;
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public Long noticeUpdate(NoticeUpdateRequestDTO noticeUpdateRequestDTO , List<String> pathList) {

        Notice notice = noticeRepository.findById(noticeUpdateRequestDTO.getNoticeNo()).orElseThrow(NullPointerException::new);
        notice.noticeUpdate(noticeUpdateRequestDTO);

        if(pathList != null) {
            for(String filePath : pathList) {
                String realPath = path.replace(dbPath,"");
                noticeImageRepository.save(NoticeImage.builder().notice(notice).noticeImagePath(filePath).noticeRealImagePath(realPath + filePath).build());
            }
        }

        return notice.getNoticeNo();
    }

    @Transactional(readOnly = true)
    @Scheduled(cron = "0 0 03 * * *")
    public void noticeImageRemove(){

        log.info("공지사항 잔여 이미지 삭제를 시작합니다.");

        log.info("공지사항 이미지 목록을 DB 에서 가져옵니다.");

        List<NoticeImage> noticeImageList = noticeImageRepository.findAll();

        log.info("공지사항 디렉토리 파일들을 가져 옵니다.");

        File dirFile = new File(path);

        File[] files = dirFile.listFiles();

        boolean flag = true;

        log.info("공지사항 잔여 이미지 삭제를 실행합니다.");

        for(File file : files) {
            flag = true;
            for(NoticeImage noticeImage : noticeImageList) {
                if(file.getName().equals(noticeImage.getNoticeImagePath().replace(dbPath,""))) {
                    flag = false;
                }
            }
            if(flag) {
                file.delete();
            }
        }

        log.info("공지 사항 파일 삭제 완료.");
    }

    @Transactional(readOnly = true)
    public NoticePrevNextResponseDTO noticePrevNextResponseDTO(Long noticeNo, int category) {
        return noticeRepository.noticePrevAndNext(noticeNo,category);
    }

}
