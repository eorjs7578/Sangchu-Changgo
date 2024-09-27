package com.d108.project.interfaces.controller;

import com.d108.project.domain.area.analytic.service.RentService;
import com.d108.project.domain.area.dto.RentDto;
import com.d108.project.interfaces.api.analytics.RentApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RentController implements RentApi {

    private final RentService rentService;

    @Override
    public ResponseEntity<RentDto> getGuRent(Long id) {
        return new ResponseEntity<>(rentService.findGuRentFee(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RentDto> getDongRent(Long id) {
        return new ResponseEntity<>(rentService.findDongRentFee(id), HttpStatus.OK);
    }
}
