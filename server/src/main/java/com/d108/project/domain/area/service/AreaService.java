package com.d108.project.domain.area.service;

import com.d108.project.domain.area.dto.AreaListDto;

import java.util.List;

public interface AreaService {
    AreaListDto getAreaList(List<Long> areaIds);
}