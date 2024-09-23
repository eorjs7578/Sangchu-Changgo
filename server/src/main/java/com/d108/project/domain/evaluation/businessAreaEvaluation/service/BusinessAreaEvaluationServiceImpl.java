package com.d108.project.domain.evaluation.businessAreaEvaluation.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
//public class BusinessAreaEvaluationServiceImpl implements BusinessAreaEvaluationService {
//    private final BusinessAreaEvaluationRepository evaluationRepository;
//    private final AreaRepository areaRepository;
//    private final MemberRepository memberRepository;
//
//    @Override
//    public BusinessAreaEvaluationDto createEvaluation(BusinessAreaEvaluationDto dto) {
//        Area area = areaRepository.findById(dto.getAreaId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid area ID"));
//        Member member = memberRepository.findById(dto.getMemberId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
//
//        BusinessAreaEvaluation evaluation = new BusinessAreaEvaluation();
//        evaluation.setArea(area);
//        evaluation.setMember(member);
//        evaluation.setAgeGroup(dto.getAgeGroup());
//        evaluation.setFootTraffic(dto.getFootTraffic());
//        evaluation.setAtmosphere(dto.getAtmosphere());
//        evaluation.setNearbyPrices(dto.getNearbyPrices());
//        evaluation.setCreatedAt(LocalDateTime.now());
//        evaluation.setUpdatedAt(LocalDateTime.now());
//
//        evaluationRepository.save(evaluation);
//
//        return dto;  // 수정 필요 시, 반환 타입 변경 가능
//    }
//
//    @Override
//    public BusinessAreaEvaluationDto updateEvaluation(Long id, BusinessAreaEvaluationDto dto) {
//        BusinessAreaEvaluation evaluation = evaluationRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));
//
//        evaluation.setAgeGroup(dto.getAgeGroup());
//        evaluation.setFootTraffic(dto.getFootTraffic());
//        evaluation.setAtmosphere(dto.getAtmosphere());
//        evaluation.setNearbyPrices(dto.getNearbyPrices());
//        evaluation.setUpdatedAt(LocalDateTime.now());
//
//        evaluationRepository.save(evaluation);
//
//        return dto;
//    }
//
//    @Override
//    public void deleteEvaluation(Long id) {
//        evaluationRepository.deleteById(id);
//    }
//
//    @Override
//    public List<BusinessAreaEvaluationDto> getEvaluationsByArea(Long areaId) {
//        return evaluationRepository.findByAreaId(areaId)
//                .stream()
//                .map(evaluation -> new BusinessAreaEvaluationDto(
//                        evaluation.getArea().getId(),
//                        evaluation.getMember().getId(),
//                        evaluation.getAgeGroup(),
//                        evaluation.getFootTraffic(),
//                        evaluation.getAtmosphere(),
//                        evaluation.getNearbyPrices()))
//                .collect(Collectors.toList());
//    }
//}

public class BusinessAreaEvaluationServiceImpl {
}