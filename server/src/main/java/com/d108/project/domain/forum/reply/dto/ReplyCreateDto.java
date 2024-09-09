package com.d108.project.domain.forum.reply.dto;

import lombok.Getter;

@Getter
public class ReplyCreateDto {
    private Long memberId;
    private String content;
}
