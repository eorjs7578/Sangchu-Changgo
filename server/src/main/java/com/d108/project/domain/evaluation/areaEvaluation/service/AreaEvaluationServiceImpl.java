package com.d108.project.domain.evaluation.areaEvaluation.service;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationDto;
import com.d108.project.domain.evaluation.areaEvaluation.entity.AreaEvaluation;
import com.d108.project.domain.evaluation.areaEvaluation.repository.AreaEvaluationRepository;
import com.d108.project.domain.global.enums.AgeGroup;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaEvaluationServiceImpl implements AreaEvaluationService {
    private final AreaEvaluationRepository evaluationRepository;
    private final AreaRepository areaRepository;
    private final MemberRepository memberRepository;

    // 평가 생성
    @Override
    public AreaEvaluationDto createEvaluation(AreaEvaluationDto dto) {
        Area area = areaRepository.findById(dto.getAreaId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid area ID"));
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        AreaEvaluation evaluation = new AreaEvaluation();
        evaluation.setArea(area);  // 변경된 부분
        evaluation.setMember(member);

        // enum 변환 .. ?
        evaluation.setAgeGroup(AgeGroup.valueOf(dto.getAgeGroup().toUpperCase()));
        evaluation.setFootTraffic(dto.getFootTraffic());
        evaluation.setAtmosphere(dto.getAtmosphere());
        evaluation.setNearbyPrices(dto.getNearbyPrices());

        evaluationRepository.save(evaluation);

        return dto;
    }

    // 평가 수정
    @Override
    public AreaEvaluationDto updateEvaluation(Long id, AreaEvaluationDto dto) {
        AreaEvaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));

        evaluation.setFootTraffic(dto.getFootTraffic());
        evaluation.setAtmosphere(dto.getAtmosphere());
        evaluation.setNearbyPrices(dto.getNearbyPrices());

        evaluationRepository.save(evaluation);

        return dto;
    }

    // 평가 삭제
    @Override
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }

    // 평가 조회
    @Override
    public List<AreaEvaluationDto> getEvaluationsByArea(Long areaId) {
        return evaluationRepository.findByAreaId(areaId).stream()
                .map(evaluation -> new AreaEvaluationDto(
                        evaluation.getArea().getId(),  // 변경된 부분
                        evaluation.getMember().getId(),
                        evaluation.getAgeGroup().name(),
                        evaluation.getFootTraffic(),
                        evaluation.getAtmosphere(),
                        evaluation.getNearbyPrices()
                )).collect(Collectors.toList());
    }
}
