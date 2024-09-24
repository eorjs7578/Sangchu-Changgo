package com.d108.project.interfaces.api.analytics;

import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/dong/analytic")
public interface DongAnalyticApi {

    @GetMapping("/foot-traffics/average/month")
    ResponseEntity<Long> getAverageFloatingPopulationPerMonth(@RequestParam Long dongId);

    @GetMapping("/foot-traffics/day-of-week")
    ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(@RequestParam Long dongId);
    @GetMapping("/foot-traffics/hour")
    ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(@RequestParam Long dongId);
    @GetMapping("/foot-traffics/month")
    ResponseEntity<FootTrafficByMonthDto> getFootTrafficByMonth(@RequestParam Long dongId);
    @GetMapping("/foot-traffics/age")
    ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(@RequestParam Long dongId);
    @GetMapping("/foot-traffics/gender")
    ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(@RequestParam Long dongId);

    @GetMapping("/industries")
    ResponseEntity<IndustryInfoDto> getIndustry(@RequestParam Long dongId);

    @GetMapping("/sales/average/annual")
    ResponseEntity<Long> getAverageAnnualRent(@RequestParam Long dongId);

    @GetMapping("/sales/quarterly")
    ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(@RequestParam Long dongId);
    @GetMapping("/sales/weekend-weekday")
    ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(@RequestParam Long dongId);
    @GetMapping("/sales/age")
    ResponseEntity<SalesByAgeDto> getSaleByAge(@RequestParam Long dongId);

    @GetMapping("/rents/floor")
    ResponseEntity<RentsByFloorDto> getRentsByFloor(@RequestParam Long dongId);
}
