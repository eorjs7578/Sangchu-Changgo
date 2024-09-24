package com.d108.project.domain.area.analytic.service;

import com.d108.project.domain.area.analytic.repository.*;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.interfaces.api.analytics.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AreaAnalyticServiceImpl implements AreaAnalyticService {

    private final AreaRepository areaRepository;
    private final StoreRepository storeRepository;
    private final SaleRepository saleRepository;
    private final ServiceRepository serviceRepository;
    private final SalesHistoryRepository salesHistoryRepository;
    private final PopulationHistoryRepository populationHistoryRepository;
    private final PopulationRepository populationRepository;

    @Override
    public Long getAverageFloatingPopulationPerMonth(Long areaId) {
        // area_id를 기반으로 해당 구역의 월 평균 유동 인구를 계산
        // DDL 파일 기준으로 population 테이블과 관련이 있을 수 있음
        return populationRepository.getAveragePopulationByAreaId(areaId);
    }

    @Override
    public FootTrafficByDayOfWeekDto getFootTrafficByDayOfWeek(Long areaId) {
        // area_id에 따라 일별 유동 인구 데이터를 조회
        // 예시: population 테이블에서 요일별 유동인구를 계산하여 반환
        return populationRepository.getFootTrafficByDayOfWeek(areaId);
    }

    @Override
    public FootTrafficByHourDto getFootTrafficByHour(Long areaId) {
        // area_id에 따라 시간별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByHour(areaId);
    }

    @Override
    public FootTrafficByAgeDto getFootTrafficByAge(Long areaId) {
        // area_id에 따라 연령대별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByAge(areaId);
    }

    @Override
    public FootTrafficByGenderDto getFootTrafficByGender(Long areaId) {
        // area_id에 따라 성별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByGender(areaId);
    }

    @Override
    public IndustryInfoDto getIndustry(Long areaId) {
        // 상권 정보 조회
        // area_id에 따른 산업 정보를 반환
        return areaRepository.getIndustryByAreaId(areaId);
    }

    @Override
    public Long getAverageAnnualRent(Long areaId) {
        // 해당 구역의 연간 평균 임대료를 조회
        // rent 테이블과 관련된 정보를 가져옴
        return rentRepository.getAverageAnnualRentByAreaId(areaId);
    }

    @Override
    public SalesByQuarterlyDto getSaleByQuarterly(Long areaId) {
        // 분기별 매출 데이터를 조회하여 반환
        return saleRepository.getSalesByQuarterly(areaId);
    }

    @Override
    public WeekendAndWeekdaySalesDto getWeekendAndWeekdaySale(Long areaId) {
        // 주말과 평일 매출 데이터를 조회하여 반환
        return saleRepository.getWeekendAndWeekdaySales(areaId);
    }

    @Override
    public SalesByAgeDto getSaleByAge(Long areaId) {
        // 연령대별 매출 데이터를 조회하여 반환
        return saleRepository.getSalesByAge(areaId);
    }

    @Override
    public RentsByFloorDto getRentsByFloor(Long areaId) {
        // 층별 임대료 데이터를 조회하여 반환
        return rentRepository.getRentsByFloor(areaId);
    }
}
