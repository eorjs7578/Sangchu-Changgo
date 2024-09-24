package com.d108.project.domain.area.repository;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.map.entity.Dong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByCode(Dong code);
}
