package com.d108.project.interfaces.controller;

import com.d108.project.domain.map.dto.AreaInfoDto;
import com.d108.project.domain.map.dto.PolygonDto;
import com.d108.project.domain.map.dto.RegionInfoDto;
import com.d108.project.domain.map.service.MapService;
import com.d108.project.interfaces.api.MapApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="지도")
@RestController
@RequiredArgsConstructor
public class MapController implements MapApi {

    private final MapService mapService;

    @Override
    public ResponseEntity<List<PolygonDto>> getGuPolygon() {
        return ResponseEntity.ok(mapService.getGuPolygon());
    }

    @Override
    public ResponseEntity<List<PolygonDto>> getDongPolygon(Long code) {
        return ResponseEntity.ok(mapService.getDongPolygon(code));
    }

    @Override
    public ResponseEntity<List<PolygonDto>> getAreaPolygon(Long code) {
        return ResponseEntity.ok(mapService.getAreaPolygon(code));
    }

    @Override
    public ResponseEntity<List<RegionInfoDto>> getDong(Long code) {
        return ResponseEntity.ok(mapService.getDongInfo(code));
    }

    @Override
    public ResponseEntity<List<RegionInfoDto>> getGu() {
        return ResponseEntity.ok(mapService.getGuInfo());
    }

    @Override
    public ResponseEntity<AreaInfoDto> getArea(Long code) {
        return ResponseEntity.ok(mapService.getAreaInfo(code));
    }
}
