package com.d108.project.domain.forum.saleStorePost.dto;

import com.d108.project.domain.global.enums.*;
import lombok.Getter;

@Getter
public class SaleStorePostUpdateDto {
    private Long id;  // 게시글 ID
    private Long boardId;  // 게시판 ID
    private Long memberId;  // 작성자(회원) ID (수정할 때 본인인지 확인 필요)
    private String title;  // 제목
    private String content;  // 내용

    // 매출 게시글에 필요한 추가 필드들
    private Long revenue;
    private Long startupPrice;
    private Long rentalPrice;
    private Long size;
    private String franchiseType;
    private String ageGroup;
    private String footTraffic;
    private String atmosphere;
    private String nearbyPrice;
    private Long desiredSalePrice;
}
