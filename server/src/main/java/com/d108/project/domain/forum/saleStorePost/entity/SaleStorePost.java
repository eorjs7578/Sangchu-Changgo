package com.d108.project.domain.forum.saleStorePost.entity;

import com.d108.project.domain.forum.board.entity.Board;
import com.d108.project.domain.global.enums.*;
import com.d108.project.domain.forum.post.entity.Post;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sale_store_posts")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id") // 상속받은 엔티티의 기본 키를 지정
public class SaleStorePost extends Post{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 창업 가격
    private Long startupPrice;
    // 임대 가격
    private Long rentalPrice;
    // 매출
    private Long revenue;

    // 평수
    private Long size;

    // 주 연령층 (여기 타입 뭐씀?)
    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    // TODO: 아래 String 이나 Enum 일지 추후 결정
    // 유동 인구
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private FootTraffic footTraffic;

    // 분위기 (여기 타입 뭐씀?)
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private Atmosphere atmosphere;

    // 주변 물가
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private NearbyPrice nearbyPrice;

    // 프랜차이즈 (여기 타입 뭐씀?)
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private FranchiseType franchiseType;


    // 원하는 판매 가격
    private Long desiredSalePrice;

    // 생성자
    public SaleStorePost(Board board, Member member, String title, String content, Long startupPrice, Long rentalPrice,
                         Long revenue, Long size, AgeGroup ageGroup, FootTraffic footTraffic, Atmosphere atmosphere,
                         NearbyPrice nearbyPrice, FranchiseType franchiseType, Long desiredSalePrice) {
        super(board, member, title, content);
        this.startupPrice = startupPrice;
        this.rentalPrice = rentalPrice;
        this.revenue = revenue;
        this.size = size;
        this.ageGroup = ageGroup;
        this.footTraffic = footTraffic;
        this.atmosphere = atmosphere;
        this.nearbyPrice = nearbyPrice;
        this.franchiseType = franchiseType;
        this.desiredSalePrice = desiredSalePrice;
    }
}
