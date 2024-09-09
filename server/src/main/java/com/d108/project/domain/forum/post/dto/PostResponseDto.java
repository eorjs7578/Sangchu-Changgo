package com.d108.project.domain.forum.post.dto;

import com.d108.project.domain.forum.post.entity.Post;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class PostResponseDto {
    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private Long view;
    // TODO: 댓글 안이어줬네..이어주기
    // TODO: 글 전체적으로 생성일 수정일 같은거 출력 안하니까 이것도 같이 dto에 넣어줘야함
    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .view(post.getView())
                .build();
    }
}
