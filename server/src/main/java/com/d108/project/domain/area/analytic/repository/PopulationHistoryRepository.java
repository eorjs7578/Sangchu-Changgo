package com.d108.project.domain.area.analytic.repository;

import com.d108.project.domain.area.analytic.entity.PopulationHistory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PopulationHistoryRepository extends JpaRepository<PopulationHistory, Long> {

    @Query("SELECT ph.populations20231, ph.populations20232, ph.populations20233, ph.populations20234, ph.populations20241 " +
            "FROM PopulationHistory ph WHERE ph.area = :areaId")
    List<Long[]> getPopulationHistoryByAreaId(@Param("areaId") Long areaId);
}

