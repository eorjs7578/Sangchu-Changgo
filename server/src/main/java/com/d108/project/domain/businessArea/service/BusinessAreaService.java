package com.d108.project.domain.businessArea.service;

import com.d108.project.domain.businessArea.dto.BusinessAreaListDto;

import java.util.List;

public interface BusinessAreaService {
    BusinessAreaListDto getBusinessAreaList(List<Long> businessAreaIds);
}
