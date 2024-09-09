package com.d108.project.domain.favorite.favoriteBusinessArea.domain;

import com.d108.project.domain.businessarea.BusinessArea;
import com.d108.project.domain.common.enums.AgeGroup;
import com.d108.project.domain.member.domain.Member;
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
    private Integer id;  // 관심상권 PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_area_id")
    private BusinessArea businessArea;

}
