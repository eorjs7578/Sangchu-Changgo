package com.d108.project.domain.favorite.favoriteBusinessArea.entity;

import com.d108.project.domain.businessArea.entity.BusinessArea;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "favorite_business_areas")
public class FavoriteBusinessArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 관심상권 PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_area_id")
    private BusinessArea businessArea;

    public static FavoriteBusinessArea toFavoriteBusinessArea(Member member, BusinessArea businessArea) {
        FavoriteBusinessArea quiz = new FavoriteBusinessArea();
        quiz.setMember(member);
        quiz.setBusinessArea(businessArea);
        return quiz;
    }
}
