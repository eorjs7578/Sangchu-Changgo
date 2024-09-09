package com.d108.project.domain.forum.reply.dto;

import com.d108.project.domain.forum.reply.domain.Reply;
import lombok.Builder;

@Builder
public class ReplyByMemberIdResponseDto {
    private Integer postId;
    private String content;

    public static ReplyByMemberIdResponseDto from(Reply reply) {
        return ReplyByMemberIdResponseDto.builder()
                .postId(reply.getPost().getId())
                .content(reply.getContent())
                .build();
    }
}
