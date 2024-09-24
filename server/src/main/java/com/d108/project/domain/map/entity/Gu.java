package com.d108.project.domain.map.entity;

import com.d108.project.domain.map.dto.PolygonDto;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

import java.util.List;
@Getter
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "gu") // 테이블 이름을 적절히 변경하세요
public class Gu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "gu_name", length = 255)
    private String guName;

    @Column(name = "x_pos")
    private Float xPos;

    @Column(name = "y_pos")
    private Float yPos;

    @Column(name = "polygon", columnDefinition = "geometry(Polygon, 4326)") // SRID는 적절히 설정
    private Geometry polygon;

}
