package com.memoryboost.domain.entity.notice;

import com.memoryboost.domain.dto.main.NoticeMainPageResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticeListResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticePrevNextResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticeResponseDTO;
import com.memoryboost.domain.entity.member.QMember;
import com.memoryboost.domain.vo.notice.NoticePrevNextVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<NoticeListResponseDTO> findByNoticeAll(int category, int page) {

        QNotice notice = QNotice.notice;

        JPAQuery<NoticeListResponseDTO> jpaQuery = queryFactory.select(Projections.fields(NoticeListResponseDTO.class,
                notice.noticeNo,notice.noticeCategory,notice.noticeTitle,notice.noticeDate))
                .from(notice);

        BooleanBuilder builder = new BooleanBuilder();

        if(category > 0) {
            builder.and(notice.noticeCategory.eq(category));
        }

        jpaQuery.where(builder).orderBy(notice.noticeDate.desc()).offset((page -1) * 10).limit(10);

        return jpaQuery.fetch();
    }

    @Override
    public int countByNoticeAll(int category) {

        QNotice notice = QNotice.notice;

        BooleanBuilder builder = new BooleanBuilder();
        if(category > 0) {
            builder.and(notice.noticeCategory.eq(category));
        }
        return (int) queryFactory.select(notice.count()).from(notice).where(builder).fetchCount();
    }

    @Override
    public NoticeResponseDTO findByNoticeNo(Long noticeNo) {

        QNotice notice = QNotice.notice;
        QNoticeImage noticeImage = QNoticeImage.noticeImage;

        NoticeResponseDTO noticeResponseDTO = queryFactory.select(Projections.fields(NoticeResponseDTO.class,
                notice.noticeNo,notice.noticeCategory,notice.noticeTitle,notice.noticeContent,notice.noticeDate))
                .from(notice).where(notice.noticeNo.eq(noticeNo)).fetchOne();

        return noticeResponseDTO;
    }

    @Override
    public List<NoticeImage> findByNoticeImage(Notice notice) {
        QNoticeImage noticeImage = QNoticeImage.noticeImage;
        return queryFactory.selectFrom(noticeImage).where(noticeImage.notice.eq(notice)).fetch();
    }

    @Override
    public List<NoticeMainPageResponseDTO> mainPageNoticeAndEventList(int category) {
        QNotice notice = QNotice.notice;
        return queryFactory.select(Projections.fields(NoticeMainPageResponseDTO.class,
                notice.noticeNo,notice.noticeTitle,notice.noticeCategory,notice.noticeDate))
                .from(notice).where(notice.noticeCategory.eq(category)).orderBy(notice.noticeNo.desc())
                .offset(0).limit(6).fetch();
    }

    @Override
    public NoticePrevNextResponseDTO noticePrevAndNext(Long noticeNo, int category) {

        QNotice notice = QNotice.notice;

        NoticePrevNextVO prev = queryFactory.select(Projections.fields(NoticePrevNextVO.class,
                notice.noticeNo,notice.noticeTitle)).from(notice).where(notice.noticeNo.lt(noticeNo).and(notice.noticeCategory.eq(category)))
                .orderBy(notice.noticeNo.desc()).offset(0).limit(1).fetchOne();

        NoticePrevNextVO next = queryFactory.select(Projections.fields(NoticePrevNextVO.class,
                notice.noticeNo, notice.noticeTitle)).from(notice).where(notice.noticeNo.gt(noticeNo).and(notice.noticeCategory.eq(category)))
                .offset(0).limit(1).fetchOne();
        return new NoticePrevNextResponseDTO(prev,next);
    }
}
