package com.d108.project.domain.area.analytic.repository;

import com.d108.project.domain.area.analytic.entity.Sale;
import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s.monthSales FROM Sale s WHERE s.area.id = :areaId AND s.service.serviceCode = :serviceCode")
    Long getSaleByMonthByAreaId(@Param("areaId") Long areaId, @Param("serviceCode") String serviceCode);

    @Query("SELECT s.monSales, s.tueSales, s.wedSales, " +
            "s.thuSales, s.friSales, s.satSales, s.sunSales " +
            "FROM Sale s WHERE s.area.id = :areaId AND s.service.serviceCode = :serviceCode")
    List<Long[]> getSaleByDayOfWeek(@Param("areaId") Long areaId, @Param("serviceCode") String serviceCode);

    @Query("SELECT s.sales0To6, s.sales6To11, s.sales11To14, " +
            "s.sales14To17, s.sales17To21, s.sales21To24 " +
            "FROM Sale s WHERE s.area.id = :areaId AND s.service.serviceCode = :serviceCode")
    List<Long[]> getSaleByHour(@Param("areaId") Long areaId, @Param("serviceCode") String serviceCode);

    @Query("SELECT s.salesIn10s, s.salesIn20s, s.salesIn30s, " +
            "s.salesIn40s, s.salesIn50s, s.salesIn60s " +
            "FROM Sale s WHERE s.area.id = :areaId AND s.service.serviceCode = :serviceCode")
    List<Long[]>  getSaleByAge(@Param("areaId") Long areaId, @Param("serviceCode") String serviceCode);

    @Query("SELECT s.manSales, s.womanSales FROM Sale s " +
            "WHERE s.area.id = :areaId AND s.service.serviceCode = :serviceCode")
    List<Long[]>  getSaleByGender(@Param("areaId") Long areaId, @Param("serviceCode") String serviceCode);

    @Query("SELECT s.weekSales, s.weekendSales FROM Sale s " +
            "WHERE s.area.id = :areaId AND s.service.serviceCode = :serviceCode")
    List<Long[]>  getSaleByWeekendAndWeekDay(@Param("areaId") Long areaId, @Param("serviceCode") String serviceCode);


    //
//    @Query("SELECT s.year, s.monthSales FROM Sales s WHERE s.dongCode = :dongId")
//    SalesByQuarterlyDto getSalesByQuarterly(@Param("dongId") Long dongId);
//
//    @Query("SELECT s.weekSales, s.weekendSales FROM Sales s WHERE s.dongCode = :dongId")
//    WeekendAndWeekdaySalesDto getWeekendAndWeekdaySales(@Param("dongId") Long dongId);
//
//    @Query("SELECT s.10Sales, s.20Sales, s.30Sales, s.40Sales, s.50Sales, s.60Sales " +
//            "FROM Sales s WHERE s.dongCode = :dongId")
//    SalesByAgeDto getSalesByAge(@Param("dongId") Long dongId);
}
