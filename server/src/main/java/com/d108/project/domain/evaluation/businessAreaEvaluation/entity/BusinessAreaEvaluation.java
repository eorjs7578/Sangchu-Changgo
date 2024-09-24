package com.d108.project.domain.evaluation.businessAreaEvaluation.entity;

import com.d108.project.domain.global.enums.AgeGroup;
import com.d108.project.domain.businessArea.entity.BusinessArea;
import com.d108.project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "business_area_evaluations")
@Getter
public class BusinessAreaEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_area_id")
    private BusinessArea businessArea;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private AgeGroup ageGroup;

    //TODO: 평가 점수 설정
}
