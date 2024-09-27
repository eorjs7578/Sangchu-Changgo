package com.d108.project.interfaces.controller;

import com.d108.project.domain.franchise.dto.FranchiseDto;
import com.d108.project.domain.franchise.dto.FranchiseFeeDto;
import com.d108.project.domain.franchise.dto.FranchiseTypeDto;
import com.d108.project.domain.franchise.service.FranchiseService;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.interfaces.api.FranchiseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="franchise")
@RestController
@RequiredArgsConstructor
@Slf4j
public class FranchiseController implements FranchiseApi {

    private final FranchiseService franchiseService;

    @Operation(summary = "프랜차이즈 분류 조회", description = "")
    @Override
    public ResponseEntity<List<FranchiseTypeDto>> getFranchiseType() {
        return new ResponseEntity<>(franchiseService.getFranchiseTypes(), HttpStatus.OK);
    }

    @Operation(summary = "프랜차이즈 조회", description = "")
    @Override
    public ResponseEntity<List<FranchiseDto>> getAllFranchises() {
        return new ResponseEntity<>(franchiseService.getAllFranchises(), HttpStatus.OK);
    }

    @Operation(summary = "프랜차이즈 이름 검색", description = "")
    @Override
    public ResponseEntity<List<FranchiseDto>> getAllFranchisesByName(String name) {
        return new ResponseEntity<>(franchiseService.getFranchisesByName(name), HttpStatus.OK);
    }

    @Operation(summary = "프랜차이즈 분류로 이름 검색", description = "")
    @Override
    public ResponseEntity<List<FranchiseDto>> getFranchisesByType(String type) {
        return new ResponseEntity<>(franchiseService.getFranchisesByType(type), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FranchiseFeeDto> getFranchiseFee(Member member, Long franchiseId, Long dongCode, Long size, Boolean floor) {
        return new ResponseEntity<>(franchiseService.getFranchiseFee(member,franchiseId,dongCode,size,floor), HttpStatus.OK);
    }


}
