package com.d108.project.domain.area.analytic.repository;

import com.d108.project.domain.area.analytic.entity.Service;
import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, String> {

    @Query("SELECT s FROM Service s JOIN Store st ON s.serviceCode = st.serviceCode WHERE st.dongCode = :dongId")
    IndustryInfoDto getIndustryByDongId(@Param("dongId") Long dongId);
}
