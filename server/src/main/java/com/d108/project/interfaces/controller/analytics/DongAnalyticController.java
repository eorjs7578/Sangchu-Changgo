package com.d108.project.interfaces.controller.analytics;

import com.d108.project.domain.area.analytic.service.DongAnalyticService;
import com.d108.project.interfaces.api.analytics.dto.*;
import com.d108.project.interfaces.api.analytics.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "동별 분석")
@RestController
@RequiredArgsConstructor
public class DongAnalyticController implements DongAnalyticApi {

    private final DongAnalyticService dongAnalyticService;

    @Override
    public ResponseEntity<Long> getAverageFloatingPopulationPerMonth(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getAverageFloatingPopulationPerMonth(dongId));
    }

    @Override
    public ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getFootTrafficByDayOfWeek(dongId));
    }

    @Override
    public ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getFootTrafficByHour(dongId));
    }

    @Override
    public ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getFootTrafficByAge(dongId));
    }

    @Override
    public ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getFootTrafficByGender(dongId));
    }

    @Override
    public ResponseEntity<IndustryInfoDto> getIndustry(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getIndustry(dongId));
    }

    @Override
    public ResponseEntity<Long> getAverageAnnualRent(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getAverageAnnualRent(dongId));
    }

    @Override
    public ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getSaleByQuarterly(dongId));
    }

    @Override
    public ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getWeekendAndWeekdaySale(dongId));
    }

    @Override
    public ResponseEntity<SalesByAgeDto> getSaleByAge(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getSaleByAge(dongId));
    }

    @Override
    public ResponseEntity<RentsByFloorDto> getRentsByFloor(Long dongId) {
        return ResponseEntity.ok(dongAnalyticService.getRentsByFloor(dongId));
    }
}
