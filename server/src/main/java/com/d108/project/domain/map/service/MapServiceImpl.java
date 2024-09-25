package com.d108.project.domain.map.service;


import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.map.dto.PolygonDto;
import com.d108.project.domain.map.entity.Dong;
import com.d108.project.domain.map.entity.Gu;
import com.d108.project.domain.map.respository.DongRepository;
import com.d108.project.domain.map.respository.GuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private final GuRepository guRepository;
    private final DongRepository dongRepository;
    private final AreaRepository areaRepository;

    @Override
    public List<PolygonDto> getGuPolygon() {
        List<Gu> gu = guRepository.findAll();

        List<PolygonDto> guDto= new ArrayList<>();
        for(Gu g :gu){
            guDto.add(PolygonDto.toDTO(g));
        }

        return guDto;
    }

    @Override
    public List<PolygonDto> getDongPolygon(Long guCode){
        Gu gu = guRepository.getReferenceById(guCode);
        List<Dong> dong = dongRepository.findByCode(gu);

        List<PolygonDto> dongDto= new ArrayList<>();
        for(Dong d :dong){
            dongDto.add(PolygonDto.toDTO(d));
        }
        return dongDto;
    }

    @Override
    public List<PolygonDto> getAreaPolygon(Long code) {
        Dong dong = dongRepository.getReferenceById(code);
        List<Area> area = areaRepository.findByCode(dong);

        List<PolygonDto> areaDto= new ArrayList<>();
        for(Area a :area){
            areaDto.add(PolygonDto.toDTO(a));
        }

        return areaDto;

    }
}
