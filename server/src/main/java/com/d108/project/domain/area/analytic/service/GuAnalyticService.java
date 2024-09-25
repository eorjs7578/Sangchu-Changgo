package com.d108.project.domain.area.analytic.service;

import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.stereotype.Service;

public interface GuAnalyticService {

    // 일별 유동 인구 수 조회
    Long getFootTrafficByDaily(Long areaId);

    // 요일별 유동 인구 수 조회
    FootTrafficByDayOfWeekDto getFootTrafficByDayOfWeek(Long areaId);

    // 시간대별 유동 인구 수 조회
    FootTrafficByHourDto getFootTrafficByHour(Long areaId);

    // 분기별 유동 인구 수 조회
    FootTrafficByMonthDto getFootTrafficByQuarterly(Long areaId);

    // 연령대별 유동 인구 수 조회
    FootTrafficByAgeDto getFootTrafficByAge(Long areaId);

    // 성별 유동 인구 수 조회
    FootTrafficByGenderDto getFootTrafficByGender(Long areaId);

    // 월별 매출 조회
    Long getSaleByMonth(Long areaId, String service);

    // 요일별 매출 조회
    SalesByDayOfWeekDto getSaleByDayOfWeek(Long areaId, String service);

    // 시간대별 매출 조회
    SalesByHourDto getSaleByHour(Long areaId, String service);

    // 분기별 매출 조회
    SalesByQuarterlyDto getSaleByQuarterly(Long areaId, String service);

    // 연령대별 매출 조회
    SalesByAgeDto getSaleByAge(Long areaId, String service);

    // 성별 매출 조회
    SalesByGenderDto getSaleByGender(Long areaId, String service);

    // 주말과 평일별 매출 조회
    WeekendAndWeekdaySalesDto getWeekendAndWeekdaySale(Long areaId, String service);

    // 유사한 업종의 가게 수 조회
    Long getNumberOfSimilarStores(Long areaId, String service);

    // 업종 정보 조회
    IndustryInfoDto getIndustryInfo(Long areaId, String service);

    RentsByFloorDto getRentsByFloor(Long areaId);
}
