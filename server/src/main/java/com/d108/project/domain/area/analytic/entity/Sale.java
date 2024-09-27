package com.d108.project.domain.area.analytic.entity;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

import jakarta.persistence.*;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "service_code")
    private ServiceType serviceType;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month_sales")
    private Long monthSales;

    @Column(name = "week_sales")
    private Long weekSales;

    @Column(name = "weekend_sales")
    private Long weekendSales;

    @Column(name = "mon_sales")
    private Long monSales;

    @Column(name = "tue_sales")
    private Long tueSales;

    @Column(name = "wed_sales")
    private Long wedSales;

    @Column(name = "thu_sales")
    private Long thuSales;

    @Column(name = "fri_sales")
    private Long friSales;

    @Column(name = "sat_sales")
    private Long satSales;

    @Column(name = "sun_sales")
    private Long sunSales;

    @Column(name = "0to6")
    private Long sales0To6;

    @Column(name = "6to11")
    private Long sales6To11;

    @Column(name = "11to14")
    private Long sales11To14;

    @Column(name = "14to17")
    private Long sales14To17;

    @Column(name = "17to21")
    private Long sales17To21;

    @Column(name = "21to24")
    private Long sales21To24;

    @Column(name = "10_sales")
    private Long salesIn10s;

    @Column(name = "20_sales")
    private Long salesIn20s;

    @Column(name = "30_sales")
    private Long salesIn30s;

    @Column(name = "40_sales")
    private Long salesIn40s;

    @Column(name = "50_sales")
    private Long salesIn50s;

    @Column(name = "60_sales")
    private Long salesIn60s;

    @Column(name = "woman_sales")
    private Long womanSales;

    @Column(name = "man_sales")
    private Long manSales;
}
