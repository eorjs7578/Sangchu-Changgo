package com.d108.project.domain.board.dto;


import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class BoardCreateDto {
    private String boardTitle;
    private String boardDescription;
    private Long userId;
}
