package com.memoryboost.domain.entity.post;

import com.memoryboost.domain.dto.post.response.PostPrevNextResponseDTO;
import com.memoryboost.domain.vo.post.PostPrevNextVO;
import com.memoryboost.service.post.PostService;
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
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    public void test(){
        log.info("로컬 테스트입니다.");
    }

//    @Test
//    public void postAllDesc(){
//
//        List<PostListResponseDTO> postListResponseDTOList = postRepository.postAllDesc(1,1);
//
//        postListResponseDTOList.forEach(post -> log.info(post.toString()));
//
//    }
//
//    @Test
//    public void postOne(){
//
//        PostRequestDTO postRequestDTO = postRepository.findByPost(1L);
//        log.info(postRequestDTO.toString());
//
//    }
//



}
