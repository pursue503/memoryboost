package com.memoryboost.domain.entity.post;

import com.memoryboost.domain.dto.post.request.PostUpdateRequestDTO;
import com.memoryboost.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_post")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    // 제목
    @Column(nullable = false)
    private String postTitle;

    // 내용
    @Column(nullable = false)
    private String postContent;

    // 작성날짜
    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date postDate;

    // 카테고리구분
    @Column(nullable = false)
    private int postCategory;

    //수정날짜
    @UpdateTimestamp
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false)
    private Date postModifiedDate;

    @Builder
    public Post(Member member, String postTitle, String postContent, int postCategory) {
        this.member = member;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postCategory = postCategory;
    }

    public void postUpdate(PostUpdateRequestDTO postUpdateRequestDTO) {
        this.postTitle = postUpdateRequestDTO.getPostTitle();
        this.postContent = postUpdateRequestDTO.getPostContent();
    }

}
