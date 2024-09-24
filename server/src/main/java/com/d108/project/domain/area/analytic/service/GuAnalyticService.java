package com.d108.project.domain.area.analytic.service;

import com.d108.project.interfaces.api.analytics.dto.*;

public interface GuAnalyticService {

    Long getAverageFloatingPopulationPerMonth(Long areaId);

    FootTrafficByDayOfWeekDto getFootTrafficByDayOfWeek(Long areaId);

    FootTrafficByHourDto getFootTrafficByHour(Long areaId);

    FootTrafficByAgeDto getFootTrafficByAge(Long areaId);

    FootTrafficByGenderDto getFootTrafficByGender(Long areaId);

    IndustryInfoDto getIndustry(Long areaId);

    Long getAverageAnnualRent(Long areaId);

    SalesByQuarterlyDto getSaleByQuarterly(Long areaId);

    WeekendAndWeekdaySalesDto getWeekendAndWeekdaySale(Long areaId);

    SalesByAgeDto getSaleByAge(Long areaId);

    RentsByFloorDto getRentsByFloor(Long areaId);
}
