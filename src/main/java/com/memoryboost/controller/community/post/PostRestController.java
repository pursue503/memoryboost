package com.memoryboost.controller.community.post;

import com.memoryboost.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostRestController {

    private final PostService postService;

    @PostMapping("/post/image-upload")
    public String noticeUpload(@RequestParam("file") MultipartFile multipartFile) {
        return postService.postFileTempSave(multipartFile);
    }

}
