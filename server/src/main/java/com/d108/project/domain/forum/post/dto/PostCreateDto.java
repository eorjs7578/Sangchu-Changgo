package com.d108.project.domain.forum.post.dto;

import lombok.Getter;

@Getter
public class PostCreateDto {
    private Long boardId;
    private String title;
    private String content;
}
