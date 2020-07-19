package com.memoryboost.domain.entity.post;

import com.memoryboost.domain.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "tb_post_reply")
@Entity
public class PostReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postReplyNo;

    @ManyToOne
    @JoinColumn(name = "POST_NO", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String postReplyContent;

    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("current_timestamp()")
    @Column(insertable = false,updatable = false)
    private Date postReplyDate;

    @Builder
    public PostReply(Post post, Member member, String postReplyContent) {
        this.post = post;
        this.member = member;
        this.postReplyContent = postReplyContent;
    }
}
