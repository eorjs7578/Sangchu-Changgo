package com.d108.project.domain.area.analytic.repository;

import com.d108.project.domain.area.analytic.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s.similarBusinessCount FROM Store s WHERE s.area.id = :areaId and s.service.serviceCode = :serviceCode")
    List<Integer> getNumberOfSimilarStores(@Param("areaId") Long areaId, @Param("serviceCode") String serviceCode);
    // 업종 정보 조회
    @Query("SELECT s.storeCount, s.openingRate, s.closingRate FROM Store s WHERE s.area.id = :areaId and s.service.serviceCode = :serviceCode")
    List<Integer[]> getIndustryInfo(@Param("areaId") Long areaId, @Param("serviceCode") String serviceCode);
}
