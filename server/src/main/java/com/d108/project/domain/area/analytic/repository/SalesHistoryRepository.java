package com.d108.project.domain.area.analytic.repository;

import com.d108.project.domain.area.analytic.entity.SalesHistory;
import com.d108.project.domain.area.analytic.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Long> {
    @Query("SELECT sh.sales20231, sh.sales20232, sh.sales20233, sh.sales20234, sh.sales20241 " +
            "FROM SalesHistory sh WHERE sh.area.id = :areaId and sh.serviceType.serviceCode = :serviceCode")
    List<Long[]> getSalesHistoryByDongId(@Param("areaId") Long areaId, @Param("serviceCode") String serviceCode);

    @Query("SELECT sh.serviceType FROM SalesHistory sh where sh.gu.id=:guCode")
    List<ServiceType> findServiceTypeByGuCode(Long guCode);

    @Query("SELECT sh.serviceType FROM SalesHistory sh where sh.dong.id=:dongCode")
    List<ServiceType> findServiceTypeByDongCode(Long dongCode);

    @Query("SELECT sh.serviceType FROM SalesHistory sh where sh.area.id=:areaCode")
    List<ServiceType> findServiceTypeByAreaCode(Long areaCode);

//    @Query("SELECT sh.sales20231, sh.sales20232, sh.sales20233, sh.sales20234, sh.sales20241 " +
//            "FROM SalesHistory sh WHERE sh.dongCode = :dongId")
//    SalesByQuarterlyDto getSalesHistoryByDongId(@Param("dongId") Long dongId);
}

