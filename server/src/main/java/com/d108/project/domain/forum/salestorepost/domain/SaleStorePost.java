package com.d108.project.domain.forum.salestorepost.domain;

import com.d108.project.domain.common.enums.AgeGroup;
import com.d108.project.domain.forum.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "sale_store_posts")
@PrimaryKeyJoinColumn(name = "id") // 상속받은 엔티티의 기본 키를 지정
public class SaleStorePost extends Post{

    // 창업 가격
    private Long startupPrice;
    // 임대 가격
    private Long RentalPrice;
    // 매출
    private Long revenue;

    // 평수
    private Integer size;
    // 주 연령층 (여기 타입 뭐씀?)
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    // 유동 인구
    private String footTraffic;

    // TODO: 아래 String 이나 Enum 일지 추후 결정
    // 분위기 (여기 타입 뭐씀?)
    private String atmosphere;
    // 주변 물가
    private String nearbyPrice;
    // 업종 (여기 타입 뭐씀?)
    private String type;
    // 원하는 판매 가격
    private Long desiredSalePrice;
}
