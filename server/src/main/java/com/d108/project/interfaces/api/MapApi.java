package com.d108.project.interfaces.api;


import com.d108.project.domain.map.dto.PolygonDto;
import com.d108.project.domain.map.dto.RegionInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/map")
public interface MapApi {
    @GetMapping("/gu")
    ResponseEntity<List<PolygonDto>> getGuPolygon();

    @GetMapping("/dong")
    ResponseEntity<List<PolygonDto>> getDongPolygon(@RequestParam Long code);

    @GetMapping("/area")
    ResponseEntity<List<PolygonDto>> getAreaPolygon(@RequestParam Long code);

    @GetMapping("/info/dong")
    ResponseEntity<List<RegionInfoDto>> getDong(@RequestParam Long code);

    @GetMapping("/info/gu")
    ResponseEntity<List<RegionInfoDto>> getGu();
}
