package com.d108.project.domain.forum.post.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostPageResponseDto {
    private List<PostResponseDto> posts;
    private long totalElements; // 전체 데이터 수
    private int totalPages; // 전체 페이지 수
    private int currentPage; // 현재 페이지 (0부터 시작)
    private int pageSize; // 한 페이지당 데이터 수
    private boolean isFirstPage; // 첫 번째 페이지 여부
    private boolean isLastPage; // 마지막 페이지 여부
}
