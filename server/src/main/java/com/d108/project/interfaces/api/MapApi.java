package com.d108.project.interfaces.api;


import com.d108.project.domain.map.dto.AreaInfoDto;
import com.d108.project.domain.map.dto.PolygonDto;
import com.d108.project.domain.map.dto.RegionInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/map")
public interface MapApi {
    @GetMapping("/gu")
    ResponseEntity<List<PolygonDto>> getGuPolygon();

    @GetMapping("/dong/{code}")
    ResponseEntity<List<PolygonDto>> getDongPolygon(@PathVariable("code") Long code);

    @GetMapping("/area/{code}")
    ResponseEntity<List<PolygonDto>> getAreaPolygon(@PathVariable("code") Long code);

    @GetMapping("/info/dong")
    ResponseEntity<List<RegionInfoDto>> getDong(@RequestParam Long code);

    @GetMapping("/info/gu")
    ResponseEntity<List<RegionInfoDto>> getGu();

    @GetMapping("api/info/area/{code}")
    ResponseEntity<AreaInfoDto> getArea(@PathVariable("code") Long code);
}
