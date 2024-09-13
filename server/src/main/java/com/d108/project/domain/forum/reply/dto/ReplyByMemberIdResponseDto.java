package com.d108.project.domain.forum.reply.dto;

import com.d108.project.domain.forum.reply.entity.Reply;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReplyByMemberIdResponseDto {
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReplyByMemberIdResponseDto from(Reply reply) {
        return ReplyByMemberIdResponseDto.builder()
                .postId(reply.getPost().getId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }
}
