package com.d108.project.domain.favorite.favoriteArea.entity;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.global.BaseTimeEntity;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "favorite_business_areas")
public class FavoriteArea extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 관심상권 PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    public static FavoriteArea toFavoriteArea(Member member, Area area) {
        FavoriteArea quiz = new FavoriteArea();
        quiz.setMember(member);
        quiz.setArea(area);
        return quiz;
    }
}
