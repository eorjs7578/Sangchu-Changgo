package com.d108.project.domain.evaluation.areaEvaluation.service;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaTypeListDto;
import com.d108.project.domain.evaluation.areaEvaluation.entity.AreaEvaluation;
import com.d108.project.domain.evaluation.areaEvaluation.repository.AreaEvaluationRepository;
import com.d108.project.domain.global.enums.AgeGroup;
import com.d108.project.domain.global.enums.Atmosphere;
import com.d108.project.domain.global.enums.FootTraffic;
import com.d108.project.domain.global.enums.NearbyPrice;
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

        // enum 변환
        evaluation.setAgeGroup(AgeGroup.fromDescription(dto.getAgeGroup().toUpperCase()));
        evaluation.setFootTraffic(FootTraffic.fromDescription(dto.getFootTraffic().toUpperCase()));
        evaluation.setAtmosphere(Atmosphere.fromDescription(dto.getAtmosphere().toUpperCase()));
        evaluation.setNearbyPrices(NearbyPrice.fromDescription(dto.getNearbyPrices().toUpperCase()));

        evaluationRepository.save(evaluation);

        return dto;
    }

    // TODO: 자기 자신것만 수정할 수 있게 수정해 주세요.
    @Override
    public AreaEvaluationDto updateEvaluation(Long id, AreaEvaluationDto dto) {
        AreaEvaluation evaluation = evaluationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));

        evaluation.setAgeGroup(AgeGroup.fromDescription(dto.getAgeGroup().toUpperCase().toUpperCase()));
        evaluation.setFootTraffic(FootTraffic.fromDescription(dto.getFootTraffic().toUpperCase()));
        evaluation.setAtmosphere(Atmosphere.fromDescription(dto.getAtmosphere().toUpperCase()));
        evaluation.setNearbyPrices(NearbyPrice.fromDescription(dto.getNearbyPrices().toUpperCase()));

        return dto;
    }

    // TODO: 자기 자신것만 삭제할 수 있게 수정해 주세요.
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
                        evaluation.getAgeGroup().getDescription(),
                        evaluation.getFootTraffic().getDescription(),
                        evaluation.getAtmosphere().getDescription(),
                        evaluation.getNearbyPrices().getDescription()
                )).collect(Collectors.toList());
    }

    @Override
    public AreaTypeListDto getEvaluationsByAreaTypeList() {
        return new AreaTypeListDto(Atmosphere.getAllDescriptions(), FootTraffic.getAllDescriptions(), NearbyPrice.getAllDescriptions(), AgeGroup.getAllDescriptions());
    }
}
