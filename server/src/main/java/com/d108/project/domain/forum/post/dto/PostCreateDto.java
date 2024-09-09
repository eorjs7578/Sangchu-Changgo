package com.d108.project.domain.forum.post.dto;

import lombok.Getter;

@Getter
public class PostCreateDto {
    private Long boardId;
    private Long memberId;
    private String title;
    private String content;
}
