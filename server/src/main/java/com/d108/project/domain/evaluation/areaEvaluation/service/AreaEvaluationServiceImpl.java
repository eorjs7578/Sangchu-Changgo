package com.d108.project.domain.evaluation.areaEvaluation.service;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationCreateDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaEvaluationUpdateDto;
import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaTypeListDto;
import com.d108.project.domain.evaluation.areaEvaluation.entity.AreaEvaluation;
import com.d108.project.domain.evaluation.areaEvaluation.repository.AreaEvaluationRepository;
import com.d108.project.domain.global.EnumUtil;
import com.d108.project.domain.global.enums.AgeGroup;
import com.d108.project.domain.global.enums.Atmosphere;
import com.d108.project.domain.global.enums.FootTraffic;
import com.d108.project.domain.global.enums.NearbyPrice;
import com.d108.project.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaEvaluationServiceImpl implements AreaEvaluationService {
    private final AreaEvaluationRepository evaluationRepository;
    private final AreaRepository areaRepository;

    // 평가 생성
    @Override
    public AreaEvaluationDto createEvaluation(Member member, Long areaId, AreaEvaluationCreateDto dto) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid area ID"));


        Optional<AreaEvaluation> evaluation = evaluationRepository.findByAreaAndMember(area, member);
        if (evaluation.isPresent()) {
            throw new IllegalArgumentException("자신의 평가가 있습니다. 기존 평가를 수정해주세요.");
        }
        AreaEvaluation newEvaluation = new AreaEvaluation();
        newEvaluation.setArea(area);  // 변경된 부분
        newEvaluation.setMember(member);

        // enum 변환
        newEvaluation.setAgeGroup(AgeGroup.fromDescription(dto.ageGroup().toUpperCase()));
        newEvaluation.setFootTraffic(FootTraffic.fromDescription(dto.footTraffic().toUpperCase()));
        newEvaluation.setAtmosphere(Atmosphere.fromDescription(dto.atmosphere().toUpperCase()));
        newEvaluation.setNearbyPrices(NearbyPrice.fromDescription(dto.nearbyPrices().toUpperCase()));

        evaluationRepository.save(newEvaluation);

        // DTO로 변환하여 반환
        return new AreaEvaluationDto(
                area.getId(),  // 상권 ID
                area.getAreaName(),  // 지역 이름
                newEvaluation.getId(), // 평가 ID
                newEvaluation.getAgeGroup().getDescription(),  // 나이 그룹 설명
                newEvaluation.getFootTraffic().getDescription(),  // 유동 인구 설명
                newEvaluation.getAtmosphere().getDescription(),  // 분위기 설명
                newEvaluation.getNearbyPrices().getDescription()  // 근처 가격 설명
        );
    }

    @Override
    public AreaEvaluationDto updateEvaluation(Member member, Long evaluationId, AreaEvaluationUpdateDto dto) {
        AreaEvaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));

        System.out.println(evaluation.getMember());
        System.out.println(member);

        if (!member.getId().equals(evaluation.getMember().getId())) {
            throw new IllegalArgumentException("자신의 것만 수정할 수 있습니다.");
        }

        evaluation.setAgeGroup(AgeGroup.fromDescription(dto.ageGroup().toUpperCase().toUpperCase()));
        evaluation.setFootTraffic(FootTraffic.fromDescription(dto.footTraffic().toUpperCase()));
        evaluation.setAtmosphere(Atmosphere.fromDescription(dto.atmosphere().toUpperCase()));
        evaluation.setNearbyPrices(NearbyPrice.fromDescription(dto.nearbyPrices().toUpperCase()));

        evaluationRepository.save(evaluation);

        // DTO로 변환하여 반환
        return new AreaEvaluationDto(
                evaluation.getArea().getId(),  // 평가 ID
                evaluation.getArea().getAreaName(),  // 평가 ID
                evaluation.getId(),
                evaluation.getAgeGroup().getDescription(),  // 나이 그룹 설명
                evaluation.getFootTraffic().getDescription(),  // 유동 인구 설명
                evaluation.getAtmosphere().getDescription(),  // 분위기 설명
                evaluation.getNearbyPrices().getDescription()  // 근처 가격 설명
        );
    }

    @Override
    public void deleteEvaluation(Member member, Long evaluationId) {
        AreaEvaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));

        if (!member.getId().equals(evaluation.getMember().getId())) {
            throw new IllegalArgumentException("자신의 것만 삭제할 수 있습니다.");
        }

        evaluationRepository.delete(evaluation);
    }

    // 평가 조회
    @Override
    public AreaEvaluationDto getEvaluationsByAreaAndMember(Member member, Long areaId) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new IllegalArgumentException("상권이 존재하지 않습니다."));

        AreaEvaluation evaluation = evaluationRepository.findByAreaAndMember(area, member)
                .orElseThrow(() -> new IllegalArgumentException("평가가 존재하지 않습니다."));

        // 평가 데이터를 DTO로 변환하여 반환
        return new AreaEvaluationDto(
                area.getId(),
                area.getAreaName(),
                evaluation.getId(),
                evaluation.getAgeGroup().getDescription(),
                evaluation.getFootTraffic().getDescription(),
                evaluation.getAtmosphere().getDescription(),
                evaluation.getNearbyPrices().getDescription()
        );
    }

    @Override
    public AreaEvaluationDto getAreaEvaluationStatistics(Long areaId) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow();

        List<AreaEvaluation> allByArea = evaluationRepository.findAllByArea(area);

        if (allByArea.isEmpty()) {
            throw new IllegalArgumentException("평가 데이터가 하나도 없는 상권입니다. 평가를 해주세요.");
        }

        return calculateAreaEvaluationStatistics(area, allByArea);
    }

    @Override
    public AreaTypeListDto getEvaluationsByAreaTypeList() {
        return new AreaTypeListDto(Atmosphere.getAllDescriptions(), FootTraffic.getAllDescriptions(), NearbyPrice.getAllDescriptions(), AgeGroup.getAllDescriptions());
    }

    private AreaEvaluationDto calculateAreaEvaluationStatistics(Area area, List<AreaEvaluation> areaEvaluations) {
        List<AgeGroup> ageGroups = areaEvaluations.stream().map(AreaEvaluation::getAgeGroup).collect(Collectors.toList());
        List<FootTraffic> footTraffics = areaEvaluations.stream().map(AreaEvaluation::getFootTraffic).collect(Collectors.toList());
        List<Atmosphere> atmospheres = areaEvaluations.stream().map(AreaEvaluation::getAtmosphere).collect(Collectors.toList());
        List<NearbyPrice> nearbyPrices = areaEvaluations.stream().map(AreaEvaluation::getNearbyPrices).collect(Collectors.toList());

        AgeGroup mostFrequentAgeGroup = EnumUtil.getMostFrequent(ageGroups);
        FootTraffic mostFrequentFootTraffic = EnumUtil.getAverageValue(footTraffics);
        Atmosphere mostFrequentAtmosphere = EnumUtil.getMostFrequent(atmospheres);
        NearbyPrice mostFrequentNearbyPrice = EnumUtil.getAverageValue(nearbyPrices);

        // AreaEvaluationDto 생성
        return new AreaEvaluationDto(
                area.getId(),
                area.getAreaName(),
                mostFrequentAgeGroup.getDescription(),
                mostFrequentFootTraffic.getDescription(),
                mostFrequentAtmosphere.getDescription(),
                mostFrequentNearbyPrice.getDescription()
        );
    }
}
