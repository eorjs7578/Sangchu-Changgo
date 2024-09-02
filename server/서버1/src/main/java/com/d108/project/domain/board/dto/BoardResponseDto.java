package com.d108.project.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long view;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
