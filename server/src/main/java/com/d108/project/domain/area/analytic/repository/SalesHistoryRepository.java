package com.d108.project.domain.area.analytic.repository;

import com.d108.project.domain.area.analytic.entity.SalesHistory;
import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {
    @Query("SELECT sh.sales20231, sh.sales20232, sh.sales20233, sh.sales20234, sh.sales20241 " +
            "FROM SalesHistory sh WHERE sh.area = :areaId and sh.service = :serviceCode")
    List<Long[]> getSalesHistoryByDongId(@Param("areaId") Long areaId, @Param("areaId") String serviceCode);

//    @Query("SELECT sh.sales20231, sh.sales20232, sh.sales20233, sh.sales20234, sh.sales20241 " +
//            "FROM SalesHistory sh WHERE sh.dongCode = :dongId")
//    SalesByQuarterlyDto getSalesHistoryByDongId(@Param("dongId") Long dongId);
}
