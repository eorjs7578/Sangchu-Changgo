package com.d108.project.domain.forum.post.dto;

import lombok.Getter;

@Getter
public class PostCreateDto {
    private Integer boardId;
    private Integer memberId;
    private String title;
    private String content;
}
