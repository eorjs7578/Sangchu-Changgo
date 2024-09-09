package com.d108.project.domain.forum.reply.dto;

import com.d108.project.domain.forum.reply.entity.Reply;
import lombok.Builder;

@Builder
public class ReplyByMemberIdResponseDto {
    private Long postId;
    private String content;

    public static ReplyByMemberIdResponseDto from(Reply reply) {
        return ReplyByMemberIdResponseDto.builder()
                .postId(reply.getPost().getId())
                .content(reply.getContent())
                .build();
    }
}
