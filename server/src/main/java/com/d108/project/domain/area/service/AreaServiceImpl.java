package com.d108.project.domain.area.service;

import com.d108.project.domain.area.dto.AreaListDto;
import com.d108.project.domain.area.dto.AreaResponseDto;
import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Override
    public AreaListDto getAreaList(List<Long> areaIds) {
        return null;
    }

    @Override
    public AreaResponseDto getAreaNameById(Long areaId) {
        Area area = areaRepository.findById(areaId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상권을 찾을 수 없습니다."));

        return AreaResponseDto.from(area);
    }
}
