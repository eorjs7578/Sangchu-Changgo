package com.d108.project.domain.forum.saleStorePost.dto;

import com.d108.project.domain.evaluation.areaEvaluation.dto.AreaTypeListDto;

import java.util.List;

public record SaleStorePostTypeListDto(AreaTypeListDto areaTypeListDto, List<String> franchiseTypes) {
}
