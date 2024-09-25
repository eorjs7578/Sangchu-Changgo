//package com.d108.project.interfaces.controller.analytics;
//
//import com.d108.project.domain.area.analytic.service.GuAnalyticService;
//import com.d108.project.interfaces.api.analytics.*;
//import com.d108.project.interfaces.api.analytics.dto.*;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//
//@Tag(name = "구별 분석")
//@RestController
//@RequiredArgsConstructor
//public class GuAnalyticController implements GuAnalyticApi {
//
//    private final GuAnalyticService guAnalyticService;
//
//    @Override
//    public ResponseEntity<Long> getFootTrafficByDaily(Long guId) {
//        return ResponseEntity.ok(guAnalyticService.getFootTrafficByDaily(guId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(Long guId) {
//        return ResponseEntity.ok(guAnalyticService.getFootTrafficByDayOfWeek(guId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(Long guId) {
//        return ResponseEntity.ok(guAnalyticService.getFootTrafficByHour(guId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByMonthDto> getFootTrafficByQuarterly(Long guId) {
//        return ResponseEntity.ok(guAnalyticService.getFootTrafficByQuarterly(guId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(Long guId) {
//        return ResponseEntity.ok(guAnalyticService.getFootTrafficByAge(guId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(Long guId) {
//        return ResponseEntity.ok(guAnalyticService.getFootTrafficByGender(guId));
//    }
//
//    @Override
//    public ResponseEntity<Long> getSaleByMonth(Long guId, String service) {
//        return ResponseEntity.ok(guAnalyticService.getSaleByMonth(guId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByDayOfWeekDto> getSaleByDayOfWeek(Long guId, String service) {
//        return ResponseEntity.ok(guAnalyticService.getSaleByDayOfWeek(guId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByHourDto> getSaleByHour(Long guId, String service) {
//        return ResponseEntity.ok(guAnalyticService.getSaleByHour(guId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(Long guId, String service) {
//        return ResponseEntity.ok(guAnalyticService.getSaleByQuarterly(guId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByAgeDto> getSaleByAge(Long guId, String service) {
//        return ResponseEntity.ok(guAnalyticService.getSaleByAge(guId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByGenderDto> getSaleByGender(Long guId, String service) {
//        return ResponseEntity.ok(guAnalyticService.getSaleByGender(guId, service));
//    }
//
//    @Override
//    public ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(Long guId, String service) {
//        return ResponseEntity.ok(guAnalyticService.getWeekendAndWeekdaySale(guId, service));
//    }
//
//    @Override
//    public ResponseEntity<Long> getNumberOfSimilarStores(Long guId, String service) {
//        return ResponseEntity.ok(guAnalyticService.getNumberOfSimilarStores(guId, service));
//    }
//
//    @Override
//    public ResponseEntity<IndustryInfoDto> getIndustryInfo(Long guId, String service) {
//        return ResponseEntity.ok(guAnalyticService.getIndustryInfo(guId, service));
//    }
//
//    @Override
//    public ResponseEntity<RentsByFloorDto> getRentsByFloor(Long guId) {
//        return ResponseEntity.ok(guAnalyticService.getRentsByFloor(guId));
//    }
//}
