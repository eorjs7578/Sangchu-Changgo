package com.d108.project.domain.forum.post.dto;

import com.d108.project.domain.forum.post.entity.Post;
import com.d108.project.domain.forum.reply.dto.ReplyResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private Long view;

    private List<ReplyResponseDto> reply;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .view(post.getView())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .reply(post.getReplies().stream()
                        .map(ReplyResponseDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
