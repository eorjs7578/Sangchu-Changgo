package com.d108.project.domain.forum.post.dto;

import com.d108.project.domain.forum.post.entity.Post;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long view;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .view(post.getView())
                .build();
    }
}
