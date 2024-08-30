package com.d108.project.domain.comment.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentCreateDto {
    private String content;
    private Long boardId;
    private Long userId;
}
