package com.d108.project.domain.area.entity;

import com.d108.project.domain.map.entity.Dong;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "area_name", length = 255)
    private String areaName;

    @ManyToOne
    @JoinColumn(name = "dong_code", referencedColumnName = "id")
    private Dong code;

    @Column(name = "x_pos")
    private Float xPos;

    @Column(name = "y_pos")
    private Float yPos;

    @Column(name = "polygon", columnDefinition = "geometry(Polygon, 4326)") // SRID는 적절히 설정
    private Geometry polygon;

}
