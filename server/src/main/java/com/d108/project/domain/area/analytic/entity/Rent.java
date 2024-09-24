package com.d108.project.domain.area.analytic.entity;

import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

import jakarta.persistence.*;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@Entity
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentId;

    @ManyToOne
    @JoinColumn(name = "gu_code")
    private Gu gu;

    @ManyToOne
    @JoinColumn(name = "dong_code")
    private Dong dong;

    @Column(name = "first_floor")
    private Long firstFloorRent;

    @Column(name = "other_floor")
    private Long otherFloorRent;
}
