package com.d108.project.interfaces.controller.analytics;

import com.d108.project.domain.area.analytic.service.AreaAnalyticService;
import com.d108.project.interfaces.api.analytics.*;
import com.d108.project.interfaces.api.analytics.dto.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상권 분석")
@RestController
@RequiredArgsConstructor
public class AreaAnalyticController implements AreaAnalyticApi {

    private final AreaAnalyticService areaAnalyticService;

    @Override
    public ResponseEntity<Long> getFootTrafficByDaily(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByDaily(areaId));
    }

    @Override
    public ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByDayOfWeek(areaId));
    }

    @Override
    public ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByHour(areaId));
    }

    @Override
    public ResponseEntity<FootTrafficByMonthDto> getFootTrafficByQuarterly(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByQuarterly(areaId));
    }

    @Override
    public ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByAge(areaId));
    }

    @Override
    public ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByGender(areaId));
    }

    @Override
    public ResponseEntity<Long> getSaleByMonth(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByMonth(areaId, service));
    }

    @Override
    public ResponseEntity<SalesByDayOfWeekDto> getSaleByDayOfWeek(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByDayOfWeek(areaId, service));
    }

    @Override
    public ResponseEntity<SalesByHourDto> getSaleByHour(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByHour(areaId, service));
    }

    @Override
    public ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByQuarterly(areaId, service));
    }

    @Override
    public ResponseEntity<SalesByAgeDto> getSaleByAge(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByAge(areaId, service));
    }

    @Override
    public ResponseEntity<SalesByGenderDto> getSaleByGender(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByGender(areaId, service));
    }

    @Override
    public ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getWeekendAndWeekdaySale(areaId, service));
    }

    @Override
    public ResponseEntity<Integer> getNumberOfSimilarStores(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getNumberOfSimilarStores(areaId, service));
    }

    @Override
    public ResponseEntity<IndustryInfoDto> getIndustryInfo(Long areaId, String service) {
        return ResponseEntity.ok(areaAnalyticService.getIndustryInfo(areaId, service));
    }
}
