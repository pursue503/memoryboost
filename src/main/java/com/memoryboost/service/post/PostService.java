package com.memoryboost.service.post;

import com.memoryboost.domain.dto.post.request.PostReplySaveRequestDTO;
import com.memoryboost.domain.dto.post.request.PostSaveRequestDTO;
import com.memoryboost.domain.dto.post.request.PostUpdateRequestDTO;
import com.memoryboost.domain.dto.post.response.PostListResponseDTO;
import com.memoryboost.domain.dto.post.response.PostPrevNextResponseDTO;
import com.memoryboost.domain.dto.post.response.PostReplyListResponseDTO;
import com.memoryboost.domain.dto.post.response.PostResponseDTO;
import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.member.MemberRepository;
import com.memoryboost.domain.entity.post.*;
import com.memoryboost.domain.vo.member.MemberCustomVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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
            log.info("post 업로드 성공");
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
    public PostResponseDTO postDetail(Long postNo) {
        return postRepository.findByPost(postNo);
    }

    @Transactional
    public Long postUpdate(PostUpdateRequestDTO postUpdateRequestDTO, List<String> pathList) {
        Post post = postRepository.findById(postUpdateRequestDTO.getPostNo()).orElseThrow(NullPointerException::new);
        post.postUpdate(postUpdateRequestDTO);

        if(pathList != null) {
            for(String filePath : pathList) {
                String realPath = path.replace(dbPath,"");
                postImageRepository.save(PostImage.builder().post(post).postImagePath(filePath).postRealPath(realPath).build());
            }
        }

        return post.getPostNo();
    }

    @Transactional
    public Long postReplySave(PostReplySaveRequestDTO postReplySaveRequestDTO , Authentication authentication) {
        MemberCustomVO memberCustomVO = (MemberCustomVO) authentication.getPrincipal();
        Member member = memberRepository.findById(memberCustomVO.getMemberId()).orElseThrow(NullPointerException::new);
        Post post = postRepository.findById(postReplySaveRequestDTO.getPostNo()).orElseThrow(NullPointerException::new);

        postReplyRepository.save(PostReply.builder().member(member).post(post).postReplyContent(postReplySaveRequestDTO.getPostReplyContent()).build());

        return post.getPostNo();
    }

    @Transactional(readOnly = true)
    public List<PostReplyListResponseDTO> postReplyList(Long postNo) {
        return postRepository.findByPostReply(postNo);
    }

    @Transactional
    public Long postReplyDelete(Long replyNo) {

        PostReply postReply = postReplyRepository.findById(replyNo).orElseThrow(NullPointerException::new);
        Post post = postRepository.findById(postReply.getPost().getPostNo()).orElseThrow(NullPointerException::new);

        postReplyRepository.delete(postReply);
        return post.getPostNo();
    }

    @Transactional(readOnly = true)
    public PostPrevNextResponseDTO postPrevNext(Long postNo,int category) {
        return postRepository.postPrevAndNext(postNo,category);
    }

    //게시글 등록중 작성을안하고 취소를 눌렀을 경우 남아있는 이미지들을 삭제.
    @Transactional(readOnly = true)
    @Scheduled(cron = "* 0 02 * * *")
    public void postImageRemove(){
        log.info("PostImageRemove 실행 합니다.");

        log.info("post image 목록을 가져 옵니다.");

        List<PostImage> postImageList = postImageRepository.findAll();

        log.info("post 디렉토리 파일들을 가져 옵니다..");

        File dirFile = new File(path);

        File[] files = dirFile.listFiles();


        boolean flag = true;

        log.info("post 잔여 이미지 삭제 실행.");

        for(File file : files) {
            flag = true;
            for(PostImage postImage : postImageList) {
                if(file.getName().equals(postImage.getPostImagePath().replace(dbPath,""))) {
                    flag = false;
                }
            }
            if(flag) {
                file.delete();
            }
        }

        log.info("post 잔여 이미지 삭제 완료.");
    }

}
