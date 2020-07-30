package com.memoryboost.domain.entity.post;

import com.memoryboost.domain.dto.main.PostMainPageResponseDTO;
import com.memoryboost.domain.dto.post.response.PostListResponseDTO;
import com.memoryboost.domain.dto.post.response.PostPrevNextResponseDTO;
import com.memoryboost.domain.dto.post.response.PostReplyListResponseDTO;
import com.memoryboost.domain.dto.post.response.PostResponseDTO;
import com.memoryboost.domain.entity.member.QMember;
import com.memoryboost.domain.vo.post.PostPrevNextVO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostListResponseDTO> postAllDesc(int category, int page) {

        QPost post = QPost.post;
        QMember member = QMember.member;

        return queryFactory.select(Projections.fields(PostListResponseDTO.class,
                post.postNo,member.memberName,post.postTitle,post.postContent,post.postDate,post.postCategory))
                .from(post)
                .leftJoin(member).on(post.member.eq(member))
                .where(post.postCategory.eq(category))
                .orderBy(post.postNo.desc())
                .offset((page -1) * 10).limit(10).fetch();
    }

    @Override
    public int countByPostAllDesc(int category) {
        QPost post = QPost.post;

        return (int) queryFactory.select(post.count()).from(post).where(post.postCategory.eq(category))
                .fetchCount();
    }

    @Override
    public List<PostImage> findByPostImage(Post post) {
        QPostImage postImage = QPostImage.postImage;
        return queryFactory.selectFrom(postImage).where(postImage.post.eq(post)).fetch();
    }

    @Override
    public List<PostReply> findByPostReply(Post post) {
        QPostReply postReply = QPostReply.postReply;
        return queryFactory.selectFrom(postReply).where(postReply.post.eq(post)).fetch();
    }

    @Override
    public PostResponseDTO findByPost(Long postNo) {

        QMember member = QMember.member;
        QPost post = QPost.post;

        return queryFactory.select(Projections.fields(PostResponseDTO.class,
                post.postNo,post.postTitle,post.postContent,post.postCategory,post.post.postDate,member.memberId,member.memberName))
                .from(post).leftJoin(member).on(post.member.eq(member))
                .where(post.postNo.eq(postNo)).fetchOne();
    }

    @Override
    public List<PostMainPageResponseDTO> mainPagePost() {
        QPost post = QPost.post;
        return queryFactory.select(Projections.fields(PostMainPageResponseDTO.class,
                post.postNo,post.postTitle,post.postCategory,post.postDate))
                .from(post).where(post.postCategory.eq(1)).orderBy(post.postNo.desc())
                .offset(0).limit(11).fetch();
    }

    @Override
    public List<PostReplyListResponseDTO> findByPostReply(Long postNo) {
        QMember member = QMember.member;
        QPost post = QPost.post;
        QPostReply postReply = QPostReply.postReply;

        return queryFactory.select(Projections.fields(PostReplyListResponseDTO.class,
                postReply.postReplyNo,member.memberName,postReply.postReplyContent,postReply.member.memberId,postReply.postReplyDate))
                .from(member,post,postReply).where(post.eq(postReply.post).and(postReply.member.eq(member))
                .and(post.postNo.eq(postNo))).orderBy(postReply.postReplyDate.desc())
                .fetch();

    }

    @Override
    public PostPrevNextResponseDTO postPrevAndNext(Long postNo, int category) {

        QPost post = QPost.post;

        PostPrevNextVO prev = queryFactory.select(Projections.fields(PostPrevNextVO.class,post.postNo,post.postTitle)).from(post).where(post.postNo.lt(postNo).and(post.postCategory.eq(category)))
                .orderBy(post.postNo.desc()).offset(0).limit(1).fetchOne();
        PostPrevNextVO next = queryFactory.select(Projections.fields(PostPrevNextVO.class,post.postNo,post.postTitle)).from(post).where(post.postNo.gt(postNo).and(post.postCategory.eq(category))).offset(0).limit(1).fetchOne();


        return new PostPrevNextResponseDTO(prev,next);
    }

}
