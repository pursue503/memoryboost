package com.memoryboost.service.post;

import com.memoryboost.domain.dto.post.request.PostSaveRequestDTO;
import com.memoryboost.domain.dto.post.request.PostUpdateRequestDTO;
import com.memoryboost.domain.dto.post.response.PostListResponseDTO;
import com.memoryboost.domain.dto.post.response.PostRequestDTO;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.post.*;
import com.memoryboost.domain.vo.member.MemberCustomVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostImageRepository postImageRepository;
    private final PostReplyRepository postReplyRepository;

    private static final String path = "/home/ec2-user/post/";
    private static final String dbPath = "/post/";

    @Transactional(readOnly = true)
    public List<PostListResponseDTO> postAllDesc(int category, int page) {

        List<PostListResponseDTO> postListResponseDTOList = postRepository.postAllDesc(category,page);

        int count = postRepository.countByPostAllDesc(category) - (page * 10 - 10);

        for(PostListResponseDTO post : postListResponseDTOList) {
            post.setPostNumber(count);
            count--;
        }
        return postListResponseDTOList;
    }

    @Transactional
    public boolean postSave(PostSaveRequestDTO postSaveRequestDTO, Authentication authentication, List<String> pathList) {

        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);

        Post post = postRepository.save(postSaveRequestDTO.toEntity(member));

        if(pathList != null) {
            for(String filePath : pathList) {
                String realPath = path.replace(dbPath,"");
                postImageRepository.save(PostImage.builder().post(post).postImagePath(filePath).postRealPath(realPath).build());
            }
        }

        return true;
    }

    public String postFileTempSave(MultipartFile multipartFile) {

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
    public boolean postDelete(Long postNo) {

        Post post = postRepository.findById(postNo).orElseThrow(NullPointerException::new);

        List<PostImage> postImageList = postRepository.findByPostImage(post);
        List<PostReply> postReplyList = postRepository.findByPostReply(post);

        for(PostImage postImage : postImageList) {
            File file = new File(postImage.getPostRealPath());
            if(file.exists()) {
                file.delete();
                postImageRepository.delete(postImage);
            }
        }

        for(PostReply postReply : postReplyList) {
            postReplyRepository.delete(postReply);
        }

        postRepository.delete(post);

        return true;
    }

    @Transactional(readOnly = true)
    public PostRequestDTO postDetail(Long postNo) {
        return postRepository.findByPost(postNo);
    }

    @Transactional
    public Long postUpdate(PostUpdateRequestDTO postUpdateRequestDTO) {
        Post post = postRepository.findById(postUpdateRequestDTO.getPostNo()).orElseThrow(NullPointerException::new);
        post.postUpdate(postUpdateRequestDTO);
        return post.getPostNo();
    }

}
