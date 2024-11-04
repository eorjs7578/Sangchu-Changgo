package com.d108.project.interfaces.controller.analytics;

import com.d108.project.domain.area.analytic.dto.*;
import com.d108.project.domain.area.analytic.service.AreaAnalyticService;
import com.d108.project.interfaces.api.analytics.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상권 분석")
@RestController
@RequiredArgsConstructor
public class AreaAnalyticController implements AreaAnalyticApi {

    private final AreaAnalyticService areaAnalyticService;

    @Operation(summary = "[ALL] 일일 유동인구 조회", description = "")
    @Override
    public ResponseEntity<Long> getFootTrafficByDaily(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByDaily(areaId));
    }

    @Operation(summary = "[ALL] 요일별 유동인구 조회", description = "월화수목금토일")
    @Override
    public ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByDayOfWeek(areaId));
    }

    @Operation(summary = "[ALL] 시간대별 유동인구 조회", description = "")
    @Override
    public ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByHour(areaId));
    }

    @Operation(summary = "[ALL] 연령별 유동인구 조회", description = "")
    @Override
    public ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByAge(areaId));
    }

    @Operation(summary = "[ALL] 남녀 유동인구 조회", description = "")
    @Override
    public ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByGender(areaId));
    }

    @Operation(summary = "[ALL] 분기별 유동인구 조회", description = "")
    @Override
    public ResponseEntity<FootTrafficByMonthDto> getFootTrafficByQuarterly(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByQuarterly(areaId));
    }

    @Operation(summary = "[ALL] 월 매출 조회", description = "")
    @Override
    public ResponseEntity<Long> getSaleByMonth(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByMonth(areaId, service));
    }

    @Operation(summary = "[ALL] 요일별 매출 조회", description = "")
    @Override
    public ResponseEntity<SalesByDayOfWeekDto> getSaleByDayOfWeek(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByDayOfWeek(areaId, service));
    }

    @Operation(summary = "[ALL] 시간별 매출 조회", description = "")
    @Override
    public ResponseEntity<SalesByHourDto> getSaleByHour(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByHour(areaId, service));
    }
    @Operation(summary = "[ALL] 연령별 매출 조회", description = "")
    @Override
    public ResponseEntity<SalesByAgeDto> getSaleByAge(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByAge(areaId, service));
    }

    @Operation(summary = "[ALL] 남녀별 매출 조회", description = "")
    @Override
    public ResponseEntity<SalesByGenderDto> getSaleByGender(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByGender(areaId, service));
    }

    @Operation(summary = "[ALL] 평일/주말 매출 조회", description = "")
    @Override
    public ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getWeekendAndWeekdaySale(areaId, service));
    }

    @Operation(summary = "[ALL] 분기별 매출 조회", description = "")
    @Override
    public ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByQuarterly(areaId, service));
    }


    @Operation(summary = "[ALL] 유사한 업종 수 조회", description = "")
    @Override
    public ResponseEntity<Integer> getNumberOfSimilarStores(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getNumberOfSimilarStores(areaId, service));
    }

    @Operation(summary = "[ALL] 업종 정보 조회", description = "industry_info는 정보 Info는 현재 정보가 무엇을 의미하는지</br>" +
            ",점포수,개업률,폐업률")

    @Override
    public ResponseEntity<IndustryInfoDto> getIndustryInfo(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getIndustryInfo(areaId, service));
    }
}
