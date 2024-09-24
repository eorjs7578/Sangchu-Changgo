package com.d108.project.domain.area.analytic.repository;

import com.d108.project.domain.area.analytic.entity.Sale;
import com.d108.project.interfaces.api.analytics.dto.*;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s.year, s.monthSales FROM Sales s WHERE s.dongCode = :dongId")
    SalesByQuarterlyDto getSalesByQuarterly(@Param("dongId") Long dongId);

    @Query("SELECT s.weekSales, s.weekendSales FROM Sales s WHERE s.dongCode = :dongId")
    WeekendAndWeekdaySalesDto getWeekendAndWeekdaySales(@Param("dongId") Long dongId);

    @Query("SELECT s.10Sales, s.20Sales, s.30Sales, s.40Sales, s.50Sales, s.60Sales " +
            "FROM Sales s WHERE s.dongCode = :dongId")
    SalesByAgeDto getSalesByAge(@Param("dongId") Long dongId);
}
