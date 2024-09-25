package com.d108.project.domain.area.analytic.repository;

import com.d108.project.domain.area.analytic.entity.Rent;
import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RentRepository extends JpaRepository<Rent, Long> {

    @Query("SELECT AVG((r.firstFloorRent + r.otherFloorRent) / 2) FROM Rent r WHERE r.dong = :dongId")
    Long getAverageAnnualRentByDongId(@Param("dongId") Long dongId);

    @Query("SELECT r.firstFloorRent, r.otherFloorRent FROM Rent r WHERE r.dong = :dongId")
    RentsByFloorDto getRentsByFloor(@Param("dongId") Long dongId);
}
