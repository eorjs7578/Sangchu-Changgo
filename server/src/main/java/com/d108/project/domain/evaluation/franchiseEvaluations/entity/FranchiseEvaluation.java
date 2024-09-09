package com.d108.project.domain.evaluation.franchiseEvaluations.entity;

import com.d108.project.domain.global.enums.AgeGroup;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.franchise.entity.Franchise;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "franchise_evaluations")
@Getter
@ToString(exclude = {"member", "franchise"})
public class FranchiseEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id")
    private Franchise franchise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    private String footTraffic;

    private String atmosphere;

    private String nearbyPrices;
}
