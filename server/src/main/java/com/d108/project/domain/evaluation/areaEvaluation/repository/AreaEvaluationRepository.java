package com.d108.project.domain.evaluation.areaEvaluation.repository;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.evaluation.areaEvaluation.entity.AreaEvaluation;
import com.d108.project.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AreaEvaluationRepository extends JpaRepository<AreaEvaluation, Long> {
    List<AreaEvaluation> findAllByArea(Area area);
    Optional<AreaEvaluation> findByAreaAndMember(Area area, Member member);

}
