package com.memoryboost.controller.community.notice;

import com.memoryboost.domain.dto.notice.request.NoticeSaveRequestDTO;
import com.memoryboost.service.notice.NoticeService;
import com.memoryboost.service.paging.PagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NoticeMoveController {

    private final NoticeService noticeService;
    private final PagingService pagingService;

    @GetMapping("/notice")
    public String noticeAllList(int category,
                                @RequestParam(value = "page", required = false, defaultValue = "1") int page , Model model) {
        model.addAttribute("notice",noticeService.noticeListResponseDTOList(category, page));
        model.addAttribute("paging",pagingService.noticePaging(category,page));

        if(category == 1) {
            return "공지사항 페이지";
        } else if(category == 2) {
            return "업데이트 페이지";
        } else {
            return "이벤트 페이지";
        }
    }

    @PostMapping("/notice")
    public String noticeSave(NoticeSaveRequestDTO noticeSaveRequestDTO , @RequestParam(value = "path", required = false) List<String> pathList) {

        try{
            if(noticeService.noticeSave(noticeSaveRequestDTO,pathList)) {
                return "redirect:/notice";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "error";
    }

    @DeleteMapping("/notice")
    @ResponseBody
    public Map<String ,Boolean> noticeDelete(@RequestParam("noticeNo") Long noticeNo) {

        Map<String , Boolean> resultMap = new HashMap<>();

        resultMap.put("result",noticeService.noticeDelete(noticeNo));

        return resultMap;
    }

    @GetMapping("/notice/detail")
    public String noticeDetail(@RequestParam("noticeNo") Long noticeNo , Model model) {

        model.addAttribute("notice", noticeService.noticeDetail(noticeNo));
        return "상세보기페이지";
    }

}
