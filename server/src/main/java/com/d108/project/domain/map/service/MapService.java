package com.d108.project.domain.map.service;

import com.d108.project.domain.map.dto.AreaInfoDto;
import com.d108.project.domain.map.dto.PolygonDto;
import com.d108.project.domain.map.dto.RegionInfoDto;

import java.util.List;

public interface MapService {

    List<PolygonDto> getGuPolygon();

    List<PolygonDto> getDongPolygon(Long code);

    List<PolygonDto> getAreaPolygon(Long code);

    List<RegionInfoDto> getGuInfo();

    List<RegionInfoDto> getDongInfo(Long code);

    AreaInfoDto getAreaInfo(Long code);
}
