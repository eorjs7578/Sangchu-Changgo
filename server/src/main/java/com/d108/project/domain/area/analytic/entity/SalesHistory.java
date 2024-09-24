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
@Table(name = "sales_history")
public class SalesHistory {

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

    @Column(name = "service_code", nullable = false)
    private String serviceCode;

    @Column(name = "sales_20231")
    private Long sales20231;

    @Column(name = "sales_20232")
    private Long sales20232;

    @Column(name = "sales_20233")
    private Long sales20233;

    // Other sales columns
}
