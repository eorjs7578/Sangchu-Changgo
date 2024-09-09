package com.d108.project.domain.forum.post.dto;

import com.d108.project.domain.forum.post.domain.Post;
import lombok.Builder;

@Builder
public class PostResponseDto {
    private Integer id;
    private String title;
    private String content;
    private Integer view;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .view(post.getView())
                .build();
    }
}