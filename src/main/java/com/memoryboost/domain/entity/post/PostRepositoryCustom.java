package com.memoryboost.domain.entity.post;

import com.memoryboost.domain.dto.main.PostMainPageResponseDTO;
import com.memoryboost.domain.dto.post.response.PostListResponseDTO;
import com.memoryboost.domain.dto.post.response.PostPrevNextResponseDTO;
import com.memoryboost.domain.dto.post.response.PostReplyListResponseDTO;
import com.memoryboost.domain.dto.post.response.PostResponseDTO;

import java.util.List;

public interface PostRepositoryCustom {

    List<PostListResponseDTO> postAllDesc(int category, int page);

    int countByPostAllDesc(int category);

    //상세 보기
    PostResponseDTO findByPost(Long postNo);
    //댓글 가져오기
    List<PostReplyListResponseDTO> findByPostReply(Long postNo);


    //삭제
    //삭제 쪽 이미지 불러오기
    List<PostImage> findByPostImage(Post post);
    //댓글도 삭제
    List<PostReply> findByPostReply(Post post);

    List<PostMainPageResponseDTO> mainPagePost();

    PostPrevNextResponseDTO postPrevAndNext(Long postNo , int category);


}
