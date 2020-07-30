package com.memoryboost.controller.community.post;

import com.memoryboost.domain.dto.post.request.PostReplySaveRequestDTO;
import com.memoryboost.domain.dto.post.request.PostSaveRequestDTO;
import com.memoryboost.domain.dto.post.request.PostUpdateRequestDTO;
import com.memoryboost.service.paging.PagingService;
import com.memoryboost.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostMoveController {

    private final PostService postService;
    private final PagingService pagingService;

    @GetMapping("/post")
    public String postList(@RequestParam(value = "category" ,required = false,defaultValue = "2") int category,
                           @RequestParam(value = "page", required = false , defaultValue = "1") int page, Model model) {

        model.addAttribute("post",postService.postAllDesc(category,page));
        model.addAttribute("paging",pagingService.postPaging(category,page));

        if(category == 1) {
            return "board/review-com";
        } else {
            return "board/estimate-com";
        }

    }

    @PostMapping("/post")
    public String postSave(PostSaveRequestDTO postSaveRequestDTO, @RequestParam(value = "file", required = false)List<String> pathList , Authentication authentication) {


        try{
            if(postService.postSave(postSaveRequestDTO,authentication,pathList)) {
                return "redirect:/post?category=" + postSaveRequestDTO.getPostCategory();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "error";
        }
        return null;
    }

    @DeleteMapping("/post")
    @ResponseBody
    public Map<String , Boolean> postDelete(@RequestParam("postNo") Long postNo) {

        Map<String , Boolean> resultMap = new HashMap<>();

        try{
            resultMap.put("result", postService.postDelete(postNo));
            return resultMap;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/post/detail")
    public String postDetail(@RequestParam("postNo") Long postNo, Model model) {

        model.addAttribute("post", postService.postDetail(postNo));
        model.addAttribute("reply",postService.postReplyList(postNo));
        return "board/post-detail";
    }

    @GetMapping("/post/update")
    public String postUpdatePage(@RequestParam("postNo") Long postNo, Model model) {

        model.addAttribute("post",postService.postDetail(postNo));
        return "board/write";
    }

    @PutMapping("/post")
    @ResponseBody
    public Long postUpdate(PostUpdateRequestDTO postUpdateRequestDTO , @RequestParam(value = "file", required = false)List<String> pathList) {
        log.info(postUpdateRequestDTO.toString());
        return postService.postUpdate(postUpdateRequestDTO,pathList);
    }

    @PostMapping("/reply")
    public String postReplySave(PostReplySaveRequestDTO postReplySaveRequestDTO , Authentication authentication ) {
        return "redirect:/post/detail?postNo=" + postService.postReplySave(postReplySaveRequestDTO, authentication);
    }

    @DeleteMapping("/reply")
    @ResponseBody
    public Long postReplyDelete(@RequestParam("replyNo") Long replyNo) {
        return postService.postReplyDelete(replyNo);
    }

}
