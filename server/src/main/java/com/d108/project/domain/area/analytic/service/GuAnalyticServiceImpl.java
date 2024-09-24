package com.d108.project.domain.area.analytic.service;

import com.d108.project.domain.area.analytic.repository.*;
import com.d108.project.domain.map.respository.GuRepository;
import com.d108.project.interfaces.api.analytics.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuAnalyticServiceImpl implements GuAnalyticService {

    private final GuRepository guRepository;
    private final StoreRepository storeRepository;
    private final SaleRepository saleRepository;
    private final ServiceRepository serviceRepository;
    private final SalesHistoryRepository salesHistoryRepository;
    private final PopulationHistoryRepository populationHistoryRepository;
    private final PopulationRepository populationRepository;
    private final RentRepository rentRepository;

    @Override
    public Long getAverageFloatingPopulationPerMonth(Long guId) {
        // gu_id를 기반으로 해당 구역의 월 평균 유동 인구를 계산
        return populationRepository.getAveragePopulationByGuId(guId);
    }

    @Override
    public FootTrafficByDayOfWeekDto getFootTrafficByDayOfWeek(Long guId) {
        // gu_id에 따라 일별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByDayOfWeek(guId);
    }

    @Override
    public FootTrafficByHourDto getFootTrafficByHour(Long guId) {
        // gu_id에 따라 시간별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByHour(guId);
    }

    @Override
    public FootTrafficByAgeDto getFootTrafficByAge(Long guId) {
        // gu_id에 따라 연령대별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByAge(guId);
    }

    @Override
    public FootTrafficByGenderDto getFootTrafficByGender(Long guId) {
        // gu_id에 따라 성별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByGender(guId);
    }

    @Override
    public IndustryInfoDto getIndustry(Long guId) {
        // 구 정보 조회
        return guRepository.getIndustryByGuId(guId);
    }

    @Override
    public Long getAverageAnnualRent(Long guId) {
        // 해당 구역의 연간 평균 임대료를 조회
        return rentRepository.getAverageAnnualRentByGuId(guId);
    }

    @Override
    public SalesByQuarterlyDto getSaleByQuarterly(Long guId) {
        // 분기별 매출 데이터를 조회하여 반환
        return salesHistoryRepository.getSalesHistoryByDongId(guId);
    }

    @Override
    public WeekendAndWeekdaySalesDto getWeekendAndWeekdaySale(Long guId) {
        // 주말과 평일 매출 데이터를 조회하여 반환
        return saleRepository.getWeekendAndWeekdaySales(guId);
    }

    @Override
    public SalesByAgeDto getSaleByAge(Long guId) {
        // 연령대별 매출 데이터를 조회하여 반환
        return saleRepository.getSalesByAge(guId);
    }

    @Override
    public RentsByFloorDto getRentsByFloor(Long guId) {
        // 층별 임대료 데이터를 조회하여 반환
        return rentRepository.getRentsByFloor(guId);
    }
}
