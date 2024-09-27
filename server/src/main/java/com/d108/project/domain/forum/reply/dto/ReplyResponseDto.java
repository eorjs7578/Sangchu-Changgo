package com.d108.project.domain.forum.reply.dto;

import com.d108.project.domain.forum.reply.entity.Reply;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReplyResponseDto {
    private Long replyId;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReplyResponseDto from(Reply reply) {
        return ReplyResponseDto.builder()
                .replyId(reply.getId())
                .memberId(reply.getMember().getId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }
}
