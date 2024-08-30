package com.d108.project.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponseDto {
    private Long boardId;
    private String boardTitle;
    private String boardDescription;
    private Long boardView;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
