package com.d108.project.interfaces.api.analytics;

import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/areas/analytic")
public interface AreaAnalyticApi {
    @GetMapping("/foot-traffics/daily/{areaId}")
    ResponseEntity<Long> getFootTrafficByDaily(@PathVariable Long areaId);

    @GetMapping("/foot-traffics/day-of-week/{areaId}")
    ResponseEntity<FootTrafficByDayOfWeekDto> getFootTrafficByDayOfWeek(@PathVariable Long areaId);

    @GetMapping("/foot-traffics/hour/{areaId}")
    ResponseEntity<FootTrafficByHourDto> getFootTrafficByHour(@PathVariable Long areaId);

    @GetMapping("/foot-traffics/age/{areaId}")
    ResponseEntity<FootTrafficByAgeDto> getFootTrafficByAge(@PathVariable Long areaId);

    @GetMapping("/foot-traffics/gender/{areaId}")
    ResponseEntity<FootTrafficByGenderDto> getFootTrafficByGender(@PathVariable Long areaId);

    @GetMapping("/foot-traffics/quarterly/{areaId}")
    ResponseEntity<FootTrafficByMonthDto> getFootTrafficByQuarterly(@PathVariable Long areaId);

    // Sales 관련 경로
    @GetMapping("/sales/month/{areaId}/{service}")
    ResponseEntity<Long> getSaleByMonth(@PathVariable Long areaId, @PathVariable String service);

    @GetMapping("/sales/day-of-week/{areaId}/{service}")
    ResponseEntity<SalesByDayOfWeekDto> getSaleByDayOfWeek(@PathVariable Long areaId, @PathVariable String service);

    @GetMapping("/sales/hour/{areaId}/{service}")
    ResponseEntity<SalesByHourDto> getSaleByHour(@PathVariable Long areaId, @PathVariable String service);

    @GetMapping("/sales/age/{areaId}/{service}")
    ResponseEntity<SalesByAgeDto> getSaleByAge(@PathVariable Long areaId, @PathVariable String service);

    @GetMapping("/sales/gender/{areaId}/{service}")
    ResponseEntity<SalesByGenderDto> getSaleByGender(@PathVariable Long areaId, @PathVariable String service);

    @GetMapping("/sales/weekend-weekday/{areaId}/{service}")
    ResponseEntity<WeekendAndWeekdaySalesDto> getWeekendAndWeekdaySale(@PathVariable Long areaId, @PathVariable String service);


    @GetMapping("/sales/quarterly/{areaId}/{service}")
    ResponseEntity<SalesByQuarterlyDto> getSaleByQuarterly(@PathVariable Long areaId, @PathVariable String service);
    // Similar Industry 관련 경로
    @GetMapping("/similar-industry/number/{areaId}/{service}")
    ResponseEntity<Integer> getNumberOfSimilarStores(@PathVariable Long areaId, @PathVariable String service);

    @GetMapping("/industries/info/{areaId}/{service}")
    ResponseEntity<IndustryInfoDto> getIndustryInfo(@PathVariable Long areaId, @PathVariable String service);

}
