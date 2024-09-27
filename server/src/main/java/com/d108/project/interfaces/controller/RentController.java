package com.d108.project.interfaces.controller;

import com.d108.project.domain.area.analytic.service.RentService;
import com.d108.project.domain.area.dto.RentDto;
import com.d108.project.interfaces.api.analytics.RentApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "임대료")
@RestController
@RequiredArgsConstructor
public class RentController implements RentApi {

    private final RentService rentService;

    @Operation(summary = "구 임대료 확인", description = "")
    @Override
    public ResponseEntity<RentDto> getGuRent(Long id) {
        return new ResponseEntity<>(rentService.findGuRentFee(id), HttpStatus.OK);
    }

    @Operation(summary = "동 임대료 확인", description = "")
    @Override
    public ResponseEntity<RentDto> getDongRent(Long id) {
        return new ResponseEntity<>(rentService.findDongRentFee(id), HttpStatus.OK);
    }
}
