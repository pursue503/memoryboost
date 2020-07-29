package com.memoryboost.domain.entity.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "tb_post_image")
@Entity
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImageNo;

    @ManyToOne
    @JoinColumn(name = "POST_NO", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String postImagePath;

    @Column(nullable = false)
    private String postRealPath;

    @Builder
    public PostImage(Post post, String postImagePath, String postRealPath) {
        this.post = post;
        this.postImagePath = postImagePath;
        this.postRealPath = postRealPath;
    }
}
