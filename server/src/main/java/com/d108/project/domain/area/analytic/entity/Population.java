package com.d108.project.domain.area.analytic.entity;

import com.d108.project.domain.area.entity.Area;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import jakarta.persistence.*;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "populations")
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gu_code")
    private Gu gu;

    @ManyToOne
    @JoinColumn(name = "dong_code")
    private Dong dong;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(name = "total_population")
    private Long totalPopulation;

    @Column(name = "man_popultaion")
    private Long manPopulation;

    @Column(name = "woman_population")
    private Long womanPopulation;

    @Column(name = "10s_population")
    private Long populationIn10s;

    @Column(name = "20s_population")
    private Long populationIn20s;

    @Column(name = "30s_population")
    private Long populationIn30s;

    @Column(name = "40s_population")
    private Long populationIn40s;

    @Column(name = "50s_population")
    private Long populationIn50s;

    @Column(name = "60s_population")
    private Long populationIn60s;

    @Column(name = "0to6_population")
    private Long population0To6;

    @Column(name = "6to11_population")
    private Long population6To11;

    @Column(name = "11to14_population")
    private Long population11To14;

    @Column(name = "14to17_population")
    private Long population14To17;

    @Column(name = "17to21_population")
    private Long population17To21;

    @Column(name = "21to24_population")
    private Long population21To24;

    @Column(name = "monday_population")
    private Long mondayPopulation;

    @Column(name = "tuesday_population")
    private Long tuesdayPopulation;

    @Column(name = "wendsday_population")
    private Long wednesdayPopulation;

    @Column(name = "thursday_population")
    private Long thursdayPopulation;

    @Column(name = "friday_population")
    private Long fridayPopulation;

    @Column(name = "saturday_population")
    private Long saturdayPopulation;

    @Column(name = "sunday_population")
    private Long sundayPopulation;
}
