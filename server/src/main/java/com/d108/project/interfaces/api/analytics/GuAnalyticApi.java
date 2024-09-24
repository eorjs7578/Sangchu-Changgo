package com.d108.project.interfaces.api.analytics;

import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/gu/analytic")
public interface GuAnalyticApi {

    @GetMapping("/foot-traffics/average/month")
    ResponseEntity<Long> getAverageFloatingPopulationPerMonth(@RequestParam Long guId);

    @GetMapping("/foot-traffics/day-of-week")
    ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(@RequestParam Long guId);
    @GetMapping("/foot-traffics/hour")
    ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(@RequestParam Long guId);
    @GetMapping("/foot-traffics/month")
    ResponseEntity<FootTrafficByMonthDto> getFootTrafficByMonth(@RequestParam Long guId);
    @GetMapping("/foot-traffics/age")
    ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(@RequestParam Long guId);
    @GetMapping("/foot-traffics/gender")
    ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(@RequestParam Long guId);

    @GetMapping("/industries")
    ResponseEntity<IndustryInfoDto> getIndustry(@RequestParam Long guId);

    @GetMapping("/sales/average/annual")
    ResponseEntity<Long> getAverageAnnualRent(@RequestParam Long guId);

    @GetMapping("/sales/quarterly")
    ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(@RequestParam Long guId);
    @GetMapping("/sales/weekend-weekday")
    ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(@RequestParam Long guId);
    @GetMapping("/sales/age")
    ResponseEntity<SalesByAgeDto> getSaleByAge(@RequestParam Long guId);

    @GetMapping("/rents/floor")
    ResponseEntity<RentsByFloorDto> getRentsByFloor(@RequestParam Long guId);
}
