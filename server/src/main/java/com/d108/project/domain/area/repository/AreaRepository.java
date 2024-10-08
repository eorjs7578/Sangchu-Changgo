package com.d108.project.domain.area.repository;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.map.entity.Dong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByCode(Dong code);
    Optional<Area> findByAreaName(String areaName);
}
