package com.d108.project.domain.evaluation.areaEvaluation.entity;

import com.d108.project.domain.global.BaseTimeEntity;
import com.d108.project.domain.global.enums.*;
import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "business_area_evaluations")
@Setter
@Getter
public class AreaEvaluation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private AgeGroup ageGroup;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private FootTraffic footTraffic;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private NearbyPrice nearbyPrices;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private Atmosphere atmosphere;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private Score score;


}
