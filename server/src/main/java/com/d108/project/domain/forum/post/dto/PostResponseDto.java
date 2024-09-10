package com.d108.project.domain.forum.post.dto;

import com.d108.project.domain.forum.post.entity.Post;
import com.d108.project.domain.forum.reply.dto.ReplyResponseDto;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Setter
public class PostResponseDto {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private Long view;

    // TODO: 댓글 안이어줬네..이어주기
    private List<ReplyResponseDto> reply;

    // TODO: 글 전체적으로 생성일 수정일 같은거 출력 안하니까 이것도 같이 dto에 넣어줘야함
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
