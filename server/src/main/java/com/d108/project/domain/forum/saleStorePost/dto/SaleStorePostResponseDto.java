package com.d108.project.domain.forum.saleStorePost.dto;

import com.d108.project.domain.forum.saleStorePost.entity.SaleStorePost;
import com.d108.project.domain.forum.reply.dto.ReplyResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class SaleStorePostResponseDto {
    private Long id;  // 게시글 ID
    private Long memberId;  // 작성자 ID
    private String title;  // 제목
    private String content;  // 내용

    // 매출 게시글 관련 필드
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

    // 댓글 정보
    private List<ReplyResponseDto> reply;

    // 조회수 필드 추가
    private Long view;

    // 생성일과 수정일 필드 추가
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // from 메서드에 조회수 추가
    public static SaleStorePostResponseDto from(SaleStorePost saleStorePost) {
        return SaleStorePostResponseDto.builder()
                .id(saleStorePost.getId())
                .memberId(saleStorePost.getMember().getId())
                .title(saleStorePost.getTitle())
                .content(saleStorePost.getContent())
                .revenue(saleStorePost.getRevenue())
                .startupPrice(saleStorePost.getStartupPrice())
                .rentalPrice(saleStorePost.getRentalPrice())
                .size(saleStorePost.getSize())

                // null 체크 추가: Enum이 null일 경우 빈 문자열 반환
                .franchiseType(saleStorePost.getFranchiseType() != null ? saleStorePost.getFranchiseType().toString() : "")
                .ageGroup(saleStorePost.getAgeGroup() != null ? saleStorePost.getAgeGroup().toString() : "")
                .footTraffic(saleStorePost.getFootTraffic() != null ? saleStorePost.getFootTraffic().toString() : "")
                .atmosphere(saleStorePost.getAtmosphere() != null ? saleStorePost.getAtmosphere().toString() : "")
                .nearbyPrice(saleStorePost.getNearbyPrice() != null ? saleStorePost.getNearbyPrice().toString() : "")

                .desiredSalePrice(saleStorePost.getDesiredSalePrice())
                .createdAt(saleStorePost.getCreatedAt())
                .updatedAt(saleStorePost.getUpdatedAt())

                // 댓글 목록 추가
                .reply(saleStorePost.getReplies().stream()
                        .map(ReplyResponseDto::from)
                        .collect(Collectors.toList()))

                // 조회수 추가
                .view(saleStorePost.getView())
                .build();
    }
}
