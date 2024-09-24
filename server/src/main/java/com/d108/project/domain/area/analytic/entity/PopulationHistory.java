package com.d108.project.domain.area.analytic.entity;

import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "population_history")
public class PopulationHistory {

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

    @Column(name = "populations_20231")
    private Long populations20231;

    @Column(name = "populations_20232")
    private Long populations20232;

    @Column(name = "populations_20233")
    private Long populations20233;

    @Column(name = "populations_20234")
    private Long populations20234;

    @Column(name = "populations_20241")
    private Long populations20241;

    @Column(name = "populations_20221")
    private Long populations20221;

    @Column(name = "populations_20222")
    private Long populations20222;

    @Column(name = "populations_20223")
    private Long populations20223;

    @Column(name = "populations_20224")
    private Long populations20224;

    @Column(name = "populations_20211")
    private Long populations20211;

    @Column(name = "populations_20212")
    private Long populations20212;

    @Column(name = "populations_20213")
    private Long populations20213;

    @Column(name = "populations_20214")
    private Long populations20214;

    @Column(name = "populations_20201")
    private Long populations20201;

    @Column(name = "populations_20202")
    private Long populations20202;

    @Column(name = "populations_20203")
    private Long populations20203;

    @Column(name = "populations_20204")
    private Long populations20204;

    // Additional population columns as needed
}

