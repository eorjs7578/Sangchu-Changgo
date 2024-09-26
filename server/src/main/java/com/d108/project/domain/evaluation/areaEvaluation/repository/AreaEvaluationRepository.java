package com.d108.project.domain.evaluation.areaEvaluation.repository;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.evaluation.areaEvaluation.entity.AreaEvaluation;
import com.d108.project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaEvaluationRepository extends JpaRepository<AreaEvaluation, Long> {
    List<AreaEvaluation> findAllByArea(Area area);
    List<AreaEvaluation> findAllByAreaAndMember(Area area, Member member);

}
