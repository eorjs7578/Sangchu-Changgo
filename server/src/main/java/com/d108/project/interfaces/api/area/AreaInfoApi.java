package com.d108.project.interfaces.api.area;

import com.d108.project.domain.area.dto.AreaResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/area-info")
public interface AreaInfoApi {

    @GetMapping()
    ResponseEntity<AreaResponseDto> getAreaById(@RequestParam("areaId") Long areaId);

    @GetMapping("/name")
    public ResponseEntity<Long> getAreaID(@RequestParam String areaName);

}
