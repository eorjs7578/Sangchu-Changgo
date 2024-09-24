package com.d108.project.domain.area.analytic.entity;

import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import com.d108.project.domain.area.entity.Area;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

import jakarta.persistence.*;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "dong_code")
    private Dong dong;

    @ManyToOne
    @JoinColumn(name = "gu_code")
    private Gu gu;

    @Column(name = "year")
    private Integer year;

    @Column(name = "store_cnt")
    private Integer storeCount;

    @Column(name = "sim_busin_cnt")
    private Integer similarBusinessCount;

    @Column(name = "opening_rate")
    private Integer openingRate;

    @Column(name = "closing_rate")
    private Integer closingRate;

    @Column(name = "opening_cnt")
    private Integer openingCount;

    @Column(name = "closing_cnt")
    private Integer closingCount;

    @Column(name = "fran_cnt")
    private Integer franchiseCount;
}

