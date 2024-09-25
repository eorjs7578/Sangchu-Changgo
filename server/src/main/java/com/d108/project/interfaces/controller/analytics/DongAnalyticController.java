//package com.d108.project.interfaces.controller.analytics;
//
//import com.d108.project.domain.area.analytic.service.DongAnalyticService;
//import com.d108.project.interfaces.api.analytics.dto.*;
//import com.d108.project.interfaces.api.analytics.*;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//
//@Tag(name = "동별 분석")
//@RestController
//@RequiredArgsConstructor
//public class DongAnalyticController implements DongAnalyticApi {
//
//    private final DongAnalyticService dongAnalyticService;
//
//    @Override
//    public ResponseEntity<Long> getFootTrafficByDaily(Long dongId) {
//        return ResponseEntity.ok(dongAnalyticService.getd(dongId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(Long dongId) {
//        return ResponseEntity.ok(dongAnalyticService.getFootTrafficByDayOfWeek(dongId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(Long dongId) {
//        return ResponseEntity.ok(dongAnalyticService.getFootTrafficByHour(dongId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByMonthDto> getFootTrafficByQuarterly(Long dongId) {
//        return ResponseEntity.ok(dongAnalyticService.getFootTrafficByQuarterly(dongId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(Long dongId) {
//        return ResponseEntity.ok(dongAnalyticService.getFootTrafficByAge(dongId));
//    }
//
//    @Override
//    public ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(Long dongId) {
//        return ResponseEntity.ok(dongAnalyticService.getFootTrafficByGender(dongId));
//    }
//
//    @Override
//    public ResponseEntity<Long> getSaleByMonth(Long dongId, String service) {
//        return ResponseEntity.ok(dongAnalyticService.getSaleByMonth(dongId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByDayOfWeekDto> getSaleByDayOfWeek(Long dongId, String service) {
//        return ResponseEntity.ok(dongAnalyticService.getSaleByDayOfWeek(dongId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByHourDto> getSaleByHour(Long dongId, String service) {
//        return ResponseEntity.ok(dongAnalyticService.getSaleByHour(dongId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(Long dongId, String service) {
//        return ResponseEntity.ok(dongAnalyticService.getSaleByQuarterly(dongId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByAgeDto> getSaleByAge(Long dongId, String service) {
//        return ResponseEntity.ok(dongAnalyticService.getSaleByAge(dongId, service));
//    }
//
//    @Override
//    public ResponseEntity<SalesByGenderDto> getSaleByGender(Long dongId, String service) {
//        return ResponseEntity.ok(dongAnalyticService.getSaleByGender(dongId, service));
//    }
//
//    @Override
//    public ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(Long dongId, String service) {
//        return ResponseEntity.ok(dongAnalyticService.getWeekendAndWeekdaySale(dongId, service));
//    }
//
//    @Override
//    public ResponseEntity<Long> getNumberOfSimilarStores(Long dongId, String service) {
//        return ResponseEntity.ok(dongAnalyticService.getNumberOfSimilarStores(dongId, service));
//    }
//
//    @Override
//    public ResponseEntity<IndustryInfoDto> getIndustryInfo(Long dongId, String service) {
//        return ResponseEntity.ok(dongAnalyticService.getIndustryInfo(dongId, service));
//    }
//
//    @Override
//    public ResponseEntity<RentsByFloorDto> getRentsByFloor(Long dongId) {
//        return ResponseEntity.ok(dongAnalyticService.getRentsByFloor(dongId));
//    }
//}
