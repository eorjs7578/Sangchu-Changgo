package com.d108.project.interfaces.controller.area;

import com.d108.project.domain.area.dto.AreaResponseDto;
import com.d108.project.domain.area.service.AreaService;
import com.d108.project.interfaces.api.area.AreaInfoApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="상권 정보")
@RequiredArgsConstructor
@RestController
public class AreaController implements AreaInfoApi {

    private final AreaService areaService;

    @Override
    public ResponseEntity<AreaResponseDto> getAreaById(Long areaId) {
        return ResponseEntity.ok(areaService.getAreaNameById(areaId));
    }
}
