package com.memoryboost.domain.dto.main;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostMainPageResponseDTO {

    private Long postNo;
    private String postTitle;
    private int postCategory;
    private Date postDate;

}
