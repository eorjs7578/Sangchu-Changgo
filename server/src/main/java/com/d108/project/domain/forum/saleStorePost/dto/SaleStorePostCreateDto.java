package com.d108.project.domain.forum.saleStorePost.dto;

import com.d108.project.domain.franchise.entity.Franchise;
import com.d108.project.domain.global.enums.*;
import lombok.Getter;


@Getter
public class SaleStorePostCreateDto {
    private Long boardId;  // 게시판 ID
    private Long memberId;  // 작성자(회원) ID
    private String title;  // 제목
    private String content;  // 내용

    // 추가적으로 매물 게시글에 필요한 필드들
    private Long revenue;
    private Long startupPrice;
    private Long rentalPrice;
    private Long size;
    private FranchiseType franchiseType;
    private AgeGroup ageGroup;
    private FootTraffic footTraffic;
    private Atmosphere atmosphere;
    private NearbyPrice nearbyPrice;
    private Long desiredSalePrice;
}
