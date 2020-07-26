package com.memoryboost.controller.review;

import com.memoryboost.domain.dto.review.ReviewSaveRequestDTO;
import com.memoryboost.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReviewRestController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public Map<String, Boolean> reviewSave(ReviewSaveRequestDTO reviewSaveRequestDTO , Authentication authentication) {

        Map<String , Boolean> resultMap = new HashMap<>();

        try{
            resultMap.put("result",reviewService.productReviewSave(reviewSaveRequestDTO,authentication));
        } catch (NullPointerException e) {
            e.printStackTrace();
            resultMap.put("result",false);
        }
        return resultMap;
    }

}
