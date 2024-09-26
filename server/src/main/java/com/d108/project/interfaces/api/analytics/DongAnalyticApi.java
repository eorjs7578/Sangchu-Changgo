package com.d108.project.interfaces.api.analytics;

import com.d108.project.domain.area.analytic.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/dong/analytic")
public interface DongAnalyticApi {
    @GetMapping("/foot-traffics/daily/{dongId}")
    ResponseEntity<Long> getFootTrafficByDaily(@PathVariable Long dongId);

    @GetMapping("/foot-traffics/day-of-week/{dongId}")
    ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(@PathVariable Long dongId);

    @GetMapping("/foot-traffics/hour/{dongId}")
    ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(@PathVariable Long dongId);

    @GetMapping("/foot-traffics/quarterly/{dongId}")
    ResponseEntity<FootTrafficByMonthDto> getFootTrafficByQuarterly(@PathVariable Long dongId);

    @GetMapping("/foot-traffics/age/{dongId}")
    ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(@PathVariable Long dongId);

    @GetMapping("/foot-traffics/gender/{dongId}")
    ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(@PathVariable Long dongId);

    // Sales 관련 경로
    @GetMapping("/sales/month/{dongId}/{service}")
    ResponseEntity<Long> getSaleByMonth(@PathVariable Long dongId, @PathVariable String service);

    @GetMapping("/sales/day-of-week/{dongId}/{service}")
    ResponseEntity<SalesByDayOfWeekDto> getSaleByDayOfWeek(@PathVariable Long dongId, @PathVariable String service);

    @GetMapping("/sales/hour/{dongId}/{service}")
    ResponseEntity<SalesByHourDto> getSaleByHour(@PathVariable Long dongId, @PathVariable String service);

    @GetMapping("/sales/quarterly/{dongId}/{service}")
    ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(@PathVariable Long dongId, @PathVariable String service);

    @GetMapping("/sales/age/{dongId}/{service}")
    ResponseEntity<SalesByAgeDto> getSaleByAge(@PathVariable Long dongId, @PathVariable String service);

    @GetMapping("/sales/gender/{dongId}/{service}")
    ResponseEntity<SalesByGenderDto> getSaleByGender(@PathVariable Long dongId, @PathVariable String service);

    @GetMapping("/sales/weekend-weekday/{dongId}/{service}")
    ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(@PathVariable Long dongId, @PathVariable String service);

    // Similar Industry 관련 경로
    @GetMapping("/similar-industry/number/{dongId}/{service}")
    ResponseEntity<Long> getNumberOfSimilarStores(@PathVariable Long dongId, @PathVariable String service);

    @GetMapping("/industries/info/{dongId}/{service}")
    ResponseEntity<IndustryInfoDto> getIndustryInfo(@PathVariable Long dongId, @PathVariable String service);

    @GetMapping("/rents/floor/{dongId}")
    ResponseEntity<RentsByFloorDto> getRentsByFloor(@PathVariable Long dongId);
}
