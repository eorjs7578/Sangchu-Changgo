package com.d108.project.domain.forum.reply.dto;

import com.d108.project.domain.forum.reply.entity.Reply;
import lombok.Builder;

@Builder
public class ReplyByPostIdResponseDto {
    private Long memberId;
    private String content;

    public static ReplyByPostIdResponseDto from(Reply reply) {
        return ReplyByPostIdResponseDto.builder()
                .memberId(reply.getMember().getId())
                .content(reply.getContent())
                .build();
    }
}
