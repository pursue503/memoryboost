package com.memoryboost.domain.dto.post.request;

import com.memoryboost.domain.entity.member.Member;
import com.memoryboost.domain.entity.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDTO {

    private String postTitle;
    private String postContent;
    private int postCategory;

    public Post toEntity(Member member){
        return Post.builder().member(member).postTitle(postTitle).postContent(postContent).postCategory(postCategory).build();
    }

    public boolean patternCheck(){

        if(postTitle.trim().equals("") || postTitle == null) {
            return true;
        }
        if(postContent.trim().equals("") || postContent == null) {
            return true;
        }
        return false;

    }

}
