package com.d108.project.domain.forum.saleStorePost.dto;

import lombok.Getter;

@Getter
public class SaleStorePostDeleteDto {
    private Long id;  // 삭제할 게시글의 ID
    private Long memberId;  // 게시글을 삭제하려는 사용자의 ID
}
