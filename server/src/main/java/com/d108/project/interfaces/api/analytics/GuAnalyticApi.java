package com.d108.project.interfaces.api.analytics;

import com.d108.project.domain.area.analytic.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/gu/analytic")
public interface GuAnalyticApi {

    @GetMapping("/foot-traffics/daily/{guId}")
    ResponseEntity<Long> getFootTrafficByDaily(@PathVariable Long guId);

    @GetMapping("/foot-traffics/day-of-week/{guId}")
    ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(@PathVariable Long guId);

    @GetMapping("/foot-traffics/hour/{guId}")
    ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(@PathVariable Long guId);

    @GetMapping("/foot-traffics/quarterly/{guId}")
    ResponseEntity<FootTrafficByMonthDto> getFootTrafficByQuarterly(@PathVariable Long guId);

    @GetMapping("/foot-traffics/age/{guId}")
    ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(@PathVariable Long guId);

    @GetMapping("/foot-traffics/gender/{guId}")
    ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(@PathVariable Long guId);

    // Sales 관련 경로
    @GetMapping("/sales/month/{guId}/{service}")
    ResponseEntity<Long> getSaleByMonth(@PathVariable Long guId, @PathVariable String service);

    @GetMapping("/sales/day-of-week/{guId}/{service}")
    ResponseEntity<SalesByDayOfWeekDto> getSaleByDayOfWeek(@PathVariable Long guId, @PathVariable String service);

    @GetMapping("/sales/hour/{guId}/{service}")
    ResponseEntity<SalesByHourDto> getSaleByHour(@PathVariable Long guId, @PathVariable String service);

    @GetMapping("/sales/quarterly/{guId}/{service}")
    ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(@PathVariable Long guId, @PathVariable String service);

    @GetMapping("/sales/age/{guId}/{service}")
    ResponseEntity<SalesByAgeDto> getSaleByAge(@PathVariable Long guId, @PathVariable String service);

    @GetMapping("/sales/gender/{guId}/{service}")
    ResponseEntity<SalesByGenderDto> getSaleByGender(@PathVariable Long guId, @PathVariable String service);

    @GetMapping("/sales/weekend-weekday/{guId}/{service}")
    ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(@PathVariable Long guId, @PathVariable String service);

    // Similar Industry 관련 경로
    @GetMapping("/similar-industry/number/{guId}/{service}")
    ResponseEntity<Long> getNumberOfSimilarStores(@PathVariable Long guId, @PathVariable String service);

    @GetMapping("/industries/info/{guId}/{service}")
    ResponseEntity<IndustryInfoDto> getIndustryInfo(@PathVariable Long guId, @PathVariable String service);

    @GetMapping("/rents/floor/{guId}")
    ResponseEntity<RentsByFloorDto> getRentsByFloor(@PathVariable Long guId);

}
