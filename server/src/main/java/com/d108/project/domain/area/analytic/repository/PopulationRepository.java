package com.d108.project.domain.area.analytic.repository;

import com.d108.project.domain.area.analytic.entity.Population;
import com.d108.project.domain.area.entity.Area;
import com.d108.project.interfaces.api.analytics.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PopulationRepository extends JpaRepository<Population, Long> {

    @Query("SELECT p.totalPopulation FROM Population p WHERE p.area.id = :areaId")
    Long getPopulationByAreaId(@Param("areaId") Long areaId);

    @Query("SELECT p.mondayPopulation, p.tuesdayPopulation, p.wednesdayPopulation, " +
            "p.thursdayPopulation, p.fridayPopulation, p.saturdayPopulation, p.sundayPopulation " +
            "FROM Population p WHERE p.area.id = :areaId")
    List<Long[]> getFootTrafficByDayOfWeekByAreaId(@Param("areaId") Long areaId);


    @Query("SELECT p.population0To6, p.population6To11, p.population11To14, " +
            "p.population14To17, p.population17To21, p.population21To24 " +
            "FROM Population p WHERE p.area.id = :areaId")
    List<Long[]> getFootTrafficByHourByAreaId(@Param("areaId") Long areaId);

    @Query("SELECT p.populationIn10s, p.populationIn20s, p.populationIn30s, " +
            "p.populationIn40s, p.populationIn50s, p.populationIn60s " +
            "FROM Population p WHERE p.area.id = :areaId")
    List<Long[]>  getFootTrafficByAgeByAreaId(@Param("areaId") Long areaId);

    @Query("SELECT p.manPopulation, p.womanPopulation FROM Population p WHERE p.area.id = :areaId")
    List<Long[]>  getFootTrafficByGenderByAreaId(@Param("areaId") Long areaId);


    //
//    @Query("SELECT p.totalPopulation FROM Population p WHERE p.dongCode = :dongId")
//    Long getAveragePopulationByDongId(@Param("dongId") Long dongId);
//
//    @Query("SELECT p.mondayPopulation, p.tuesdayPopulation, p.wendsdayPopulation, " +
//            "p.thursdayPopulation, p.fridayPopulation, p.saturdayPopulation, p.sundayPopulation " +
//            "FROM Population p WHERE p.dongCode = :dongId")
//    FootTrafficByDayOfWeekDto getFootTrafficByDayOfWeek(@Param("dongId") Long dongId);
//
//    @Query("SELECT p.0to6Population, p.6to11Population, p.11to14Population, " +
//            "p.14to17Population, p.17to21Population, p.21to24Population " +
//            "FROM Population p WHERE p.dongCode = :dongId")
//    FootTrafficByHourDto getFootTrafficByHour(@Param("dongId") Long dongId);
//
//    @Query("SELECT p.10sPopulation, p.20sPopulation, p.30sPopulation, " +
//            "p.40sPopulation, p.50sPopulation, p.60sPopulation " +
//            "FROM Population p WHERE p.dongCode = :dongId")
//    FootTrafficByAgeDto getFootTrafficByAge(@Param("dongId") Long dongId);
//
//    @Query("SELECT p.manPopulation, p.womanPopulation FROM Population p WHERE p.dongCode = :dongId")
//    FootTrafficByGenderDto getFootTrafficByGender(@Param("dongId") Long dongId);
}
