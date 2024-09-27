package com.d108.project.domain.area.analytic.service;

import com.d108.project.domain.area.analytic.entity.ServiceType;

import java.util.List;

public interface ServiceTypeService {

    List<ServiceType> findGuServiceTypes(Long id);
    List<ServiceType> findDongServiceTypes(Long id);
    List<ServiceType> findAreaServiceTypes(Long id);

}
