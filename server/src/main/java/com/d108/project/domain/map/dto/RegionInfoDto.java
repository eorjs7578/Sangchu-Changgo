package com.d108.project.domain.map.dto;

import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import lombok.Builder;


@Builder
public record RegionInfoDto(Long code, String name) {
    public static RegionInfoDto to(Dong dong) {
        return RegionInfoDto.builder()
                .code(dong.getId())
                .name(dong.getDongName())
                .build();
    }
    public static RegionInfoDto to(Gu gu) {
        return RegionInfoDto.builder()
                .code(gu.getId())
                .name(gu.getGuName())
                .build();
    }
}
