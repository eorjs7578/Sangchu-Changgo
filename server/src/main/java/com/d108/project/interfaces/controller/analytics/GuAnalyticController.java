package com.d108.project.interfaces.controller.analytics;

import com.d108.project.domain.area.analytic.service.GuAnalyticService;
import com.d108.project.interfaces.api.analytics.*;
import com.d108.project.interfaces.api.analytics.dto.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "구별 분석")
@RestController
@RequiredArgsConstructor
public class GuAnalyticController implements GuAnalyticApi {

    private final GuAnalyticService guAnalyticService;

    @Override
    public ResponseEntity<Long> getAverageFloatingPopulationPerMonth(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getAverageFloatingPopulationPerMonth(guId));
    }

    @Override
    public ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getFootTrafficByDayOfWeek(guId));
    }

    @Override
    public ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getFootTrafficByHour(guId));
    }

    @Override
    public ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getFootTrafficByAge(guId));
    }

    @Override
    public ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getFootTrafficByGender(guId));
    }

    @Override
    public ResponseEntity<IndustryInfoDto> getIndustry(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getIndustry(guId));
    }

    @Override
    public ResponseEntity<Long> getAverageAnnualRent(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getAverageAnnualRent(guId));
    }

    @Override
    public ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getSaleByQuarterly(guId));
    }

    @Override
    public ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getWeekendAndWeekdaySale(guId));
    }

    @Override
    public ResponseEntity<SalesByAgeDto> getSaleByAge(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getSaleByAge(guId));
    }

    @Override
    public ResponseEntity<RentsByFloorDto> getRentsByFloor(Long guId) {
        return ResponseEntity.ok(guAnalyticService.getRentsByFloor(guId));
    }
}
