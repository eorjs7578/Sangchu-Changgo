package com.d108.project.interfaces.controller;

import com.d108.project.domain.area.analytic.entity.ServiceType;
import com.d108.project.domain.area.analytic.service.ServiceTypeService;
import com.d108.project.interfaces.api.ServiceTypeApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="업종 정보 확인")
@RestController
@RequiredArgsConstructor
public class ServiceTypeController implements ServiceTypeApi {

    private final ServiceTypeService serviceTypeService;

    @Operation(summary = "구 업종 정보 확인", description = "")
    @Override
    public ResponseEntity<List<ServiceType>> getGuServiceType(Long guCode) {
        return new ResponseEntity<>(serviceTypeService.findGuServiceTypes(guCode), HttpStatus.OK);
    }

    @Operation(summary = "동 업종 정보 확인", description = "")
    @Override
    public ResponseEntity<List<ServiceType>> getDongServiceType(Long dongCode) {
        return new ResponseEntity<>(serviceTypeService.findDongServiceTypes(dongCode), HttpStatus.OK);
    }

    @Operation(summary = "상권 업종 정보 확인", description = "")
    @Override
    public ResponseEntity<List<ServiceType>> getAreaServiceType(Long areaCode) {
        return new ResponseEntity<>(serviceTypeService.findAreaServiceTypes(areaCode), HttpStatus.OK);
    }
}
