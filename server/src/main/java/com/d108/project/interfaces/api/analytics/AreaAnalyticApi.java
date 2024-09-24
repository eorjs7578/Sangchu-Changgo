package com.d108.project.interfaces.api.analytics;

import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/areas/analytic")
public interface AreaAnalyticApi {

    // ?? 며칠 단위를 가져와야함??
    @GetMapping("/foot-traffics/average/month")
    ResponseEntity<Long> getAverageFloatingPopulationPerMonth(@RequestParam Long areaId);

    @GetMapping("/foot-traffics/day-of-week")
    ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(@RequestParam Long areaId);
    @GetMapping("/foot-traffics/hour")
    ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(@RequestParam Long areaId);
    @GetMapping("/foot-traffics/month")
    ResponseEntity<FootTrafficByMonthDto> getFootTrafficByMonth(@RequestParam Long areaId);
    @GetMapping("/foot-traffics/age")
    ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(@RequestParam Long areaId);
    @GetMapping("/foot-traffics/gender")
    ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(@RequestParam Long areaId);

    @GetMapping("/industries")
    ResponseEntity<IndustryInfoDto> getIndustry(@RequestParam Long areaId);

    // 얼마나 가져옴??
    @GetMapping("/sales/average/annual")
    ResponseEntity<Long> getAverageAnnualRent(@RequestParam Long areaId);

    @GetMapping("/sales/quarterly")
    ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(@RequestParam Long areaId);
    @GetMapping("/sales/weekend-weekday")
    ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(@RequestParam Long areaId);
    @GetMapping("/sales/age")
    ResponseEntity<SalesByAgeDto> getSaleByAge(@RequestParam Long areaId);

    @GetMapping("/rents/floor")
    ResponseEntity<RentsByFloorDto> getRentsByFloor(@RequestParam Long areaId);
}
