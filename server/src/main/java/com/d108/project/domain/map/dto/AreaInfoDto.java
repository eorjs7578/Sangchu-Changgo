package com.d108.project.domain.map.dto;

import com.d108.project.domain.area.entity.Area;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AreaInfoDto {
    private Long id;
    private String name;
    private Long dongCode;
    private Long guCode;
    private Float xPos;
    private Float yPos;
    private int size;

    @Builder
    public AreaInfoDto(Long id, String name,Long dongCode, Long guCode, Float xPos, Float yPos, int size){
        this.id = id;
        this.name = name;
        this.dongCode = dongCode;
        this.guCode = guCode;
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
    }

    public static AreaInfoDto toDto(Area area){
        return AreaInfoDto.builder()
                .id(area.getId())
                .name(area.getAreaName())
                .dongCode(area.getCode().getId())
                .guCode(area.getCode().getCode().getId())
                .xPos(area.getXPos())
                .yPos(area.getYPos())
                .size(2)
                .build();
    }
}
