package com.d108.project.domain.favorite.favoriteFranchise.dto;

import com.d108.project.domain.franchise.dto.FranchiseFeeDto;
import com.d108.project.domain.map.dto.RegionInfoDto;
import lombok.Builder;

@Builder
public record FavoriteFranchiseResponseDto(
        FranchiseFeeDto franchiseFeeDto,
        RegionInfoDto gu,
        RegionInfoDto dong,
        Long size,
        Boolean floor
) {
    public static FavoriteFranchiseResponseDto from(FranchiseFeeDto franchiseFeeDto,
                                                    RegionInfoDto gu, RegionInfoDto dong,
                                                    Long size, Boolean floor) {
        return FavoriteFranchiseResponseDto.builder()
                .franchiseFeeDto(franchiseFeeDto)
                .gu(gu)
                .dong(dong)
                .size(size)
                .floor(floor)
                .build();
    }
}
