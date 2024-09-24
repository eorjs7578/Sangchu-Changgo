package com.d108.project.domain.area.analytic.service;

import com.d108.project.domain.area.analytic.repository.*;
import com.d108.project.domain.area.repository.DongRepository;
import com.d108.project.interfaces.api.analytics.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DongAnalyticServiceImpl implements DongAnalyticService {

    private final DongRepository dongRepository;
    private final StoreRepository storeRepository;
    private final SaleRepository saleRepository;
    private final ServiceRepository serviceRepository;
    private final SalesHistoryRepository salesHistoryRepository;
    private final PopulationHistoryRepository populationHistoryRepository;
    private final PopulationRepository populationRepository;
    private final RentRepository rentRepository;

    @Override
    public Long getAverageFloatingPopulationPerMonth(Long dongId) {
        // dong_id를 기반으로 해당 구역의 월 평균 유동 인구를 계산
        return populationRepository.getAveragePopulationByDongId(dongId);
    }

    @Override
    public FootTrafficByDayOfWeekDto getFootTrafficByDayOfWeek(Long dongId) {
        // dong_id에 따라 일별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByDayOfWeek(dongId);
    }

    @Override
    public FootTrafficByHourDto getFootTrafficByHour(Long dongId) {
        // dong_id에 따라 시간별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByHour(dongId);
    }

    @Override
    public FootTrafficByAgeDto getFootTrafficByAge(Long dongId) {
        // dong_id에 따라 연령대별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByAge(dongId);
    }

    @Override
    public FootTrafficByGenderDto getFootTrafficByGender(Long dongId) {
        // dong_id에 따라 성별 유동 인구 데이터를 조회
        return populationRepository.getFootTrafficByGender(dongId);
    }

    @Override
    public IndustryInfoDto getIndustry(Long dongId) {
        // 동 정보 조회
        return dongRepository.getIndustryByDongId(dongId);
    }

    @Override
    public Long getAverageAnnualRent(Long dongId) {
        // 해당 동의 연간 평균 임대료를 조회
        return rentRepository.getAverageAnnualRentByDongId(dongId);
    }

    @Override
    public SalesByQuarterlyDto getSaleByQuarterly(Long dongId) {
        // 분기별 매출 데이터를 조회하여 반환
        return saleRepository.getSalesByQuarterly(dongId);
    }

    @Override
    public WeekendAndWeekdaySalesDto getWeekendAndWeekdaySale(Long dongId) {
        // 주말과 평일 매출 데이터를 조회하여 반환
        return saleRepository.getWeekendAndWeekdaySales(dongId);
    }

    @Override
    public SalesByAgeDto getSaleByAge(Long dongId) {
        // 연령대별 매출 데이터를 조회하여 반환
        return saleRepository.getSalesByAge(dongId);
    }

    @Override
    public RentsByFloorDto getRentsByFloor(Long dongId) {
        // 층별 임대료 데이터를 조회하여 반환
        return rentRepository.getRentsByFloor(dongId);
    }
}
