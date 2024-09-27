package com.d108.project.domain.area.dto;

import com.d108.project.domain.area.analytic.entity.Rent;
import lombok.Builder;

@Builder
public record RentDto(Long firstFloor, Long otherFloor) {
    public static RentDto to(Rent rent) {
        return RentDto.builder()
                .firstFloor(rent.getFirstFloorRent())
                .otherFloor(rent.getOtherFloorRent())
                .build();
    }
}
