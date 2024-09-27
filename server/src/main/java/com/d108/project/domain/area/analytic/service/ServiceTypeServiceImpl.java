package com.d108.project.domain.area.analytic.service;

import com.d108.project.domain.area.analytic.entity.ServiceType;
import com.d108.project.domain.area.analytic.repository.SalesHistoryRepository;
import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import com.d108.project.domain.map.respository.DongRepository;
import com.d108.project.domain.map.respository.GuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final SalesHistoryRepository salesHistoryRepository;
    private final GuRepository guRepository;
    private final DongRepository dongRepository;
    private final AreaRepository areaRepository;


    @Override
    public List<ServiceType> findGuServiceTypes(Long id) {
        Gu gu = guRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 구입니다."));
        return salesHistoryRepository.findServiceTypeByGuCode(gu.getId());
    }

    @Override
    public List<ServiceType> findDongServiceTypes(Long id) {
        Dong dong = dongRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 동입니다."));
        return salesHistoryRepository.findServiceTypeByDongCode(dong.getId());

    }

    @Override
    public List<ServiceType> findAreaServiceTypes(Long id) {
        Area area = areaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 동입니다."));
        return salesHistoryRepository.findServiceTypeByAreaCode(area.getId());
    }
}
