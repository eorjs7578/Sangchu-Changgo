package com.d108.project.domain.evaluation.franchiseevaluations.domain;

import com.d108.project.domain.common.enums.AgeGroup;
import com.d108.project.domain.member.domain.Member;
import com.d108.project.domain.franchise.domain.Franchise;
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
    private Integer id;

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
