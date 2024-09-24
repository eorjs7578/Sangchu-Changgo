package com.d108.project.domain.map.dto;

import com.d108.project.config.GeometrySerializer;
import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import org.locationtech.jts.geom.Geometry;

public class PolygonDto {
    private Long id;

    private String name;

    private Long code;

    private Float xPos;

    private Float yPos;

    @JsonSerialize(using = GeometrySerializer.class)
    private Geometry polygon;

    @Builder
    public PolygonDto(Long id, String name, long code, Float xPos, Float yPos, Geometry polygon) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.xPos = xPos;
        this.yPos = yPos;
        this.polygon = polygon;
    }

    public static PolygonDto toDTO(Gu gu){
        return PolygonDto.builder()
                .id(gu.getId())
                .name(gu.getGuName())
                .yPos(gu.getYPos())
                .xPos(gu.getXPos())
                .polygon(gu.getPolygon())
                .build();
    }

    public static PolygonDto toDTO(Dong dong){
        return PolygonDto.builder()
                .id(dong.getId())
                .name(dong.getDongName())
                .code(dong.getCode().getId())
                .yPos(dong.getYPos())
                .xPos(dong.getXPos())
                .polygon(dong.getPolygon())
                .build();
    }

    public static PolygonDto toDTO(Area area){
        return PolygonDto.builder()
                .id(area.getId())
                .name(area.getAreaName())
                .code(area.getCode().getId())
                .yPos(area.getYPos())
                .xPos(area.getXPos())
                .polygon(area.getPolygon())
                .build();
    }
}
