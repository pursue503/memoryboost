package com.memoryboost.domain.entity.notice;

import com.memoryboost.domain.dto.notice.response.NoticeListResponseDTO;
import com.memoryboost.domain.dto.notice.response.NoticeResponseDTO;
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
                .from(notice).fetchOne();

        List<String> noticeImagePath = queryFactory.select(noticeImage.noticeImagePath).from(noticeImage)
                .leftJoin(notice).on(noticeImage.notice.eq(notice)).where(notice.noticeNo.eq(noticeNo)).fetch();

        if(noticeImage == null) {
            noticeImagePath = new ArrayList<>();
        }

        noticeResponseDTO.setNoticeImagePath(noticeImagePath);

        return noticeResponseDTO;
    }

    @Override
    public List<NoticeImage> findByNoticeImage(Notice notice) {
        QNoticeImage noticeImage = QNoticeImage.noticeImage;
        return queryFactory.selectFrom(noticeImage).where(noticeImage.notice.eq(notice)).fetch();
    }
}
