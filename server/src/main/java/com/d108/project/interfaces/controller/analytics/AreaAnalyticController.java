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
    public ResponseEntity<Long> getAverageFloatingPopulationPerMonth(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getAverageFloatingPopulationPerMonth(areaId));
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
    public ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByAge(areaId));
    }

    @Override
    public ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getFootTrafficByGender(areaId));
    }

    @Override
    public ResponseEntity<IndustryInfoDto> getIndustry(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getIndustry(areaId));
    }

    @Override
    public ResponseEntity<Long> getAverageAnnualRent(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getAverageAnnualRent(areaId));
    }

    @Override
    public ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByQuarterly(areaId));
    }

    @Override
    public ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getWeekendAndWeekdaySale(areaId));
    }

    @Override
    public ResponseEntity<SalesByAgeDto> getSaleByAge(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getSaleByAge(areaId));
    }

    @Override
    public ResponseEntity<RentsByFloorDto> getRentsByFloor(Long areaId) {
        return ResponseEntity.ok(areaAnalyticService.getRentsByFloor(areaId));
    }
}
