package com.memoryboost.controller.community.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NoticeRestController {

    @PostMapping("/notice/image-upload")
    public String noticeUpload(@RequestParam("file")MultipartFile multipartFile) {

        return null;
    }

}
