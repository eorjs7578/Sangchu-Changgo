package com.d108.project.domain.map.service;


import com.d108.project.domain.area.entity.Area;
import com.d108.project.domain.area.repository.AreaRepository;
import com.d108.project.domain.map.dto.AreaInfoDto;
import com.d108.project.domain.map.dto.PolygonDto;
import com.d108.project.domain.map.dto.RegionInfoDto;
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
        List<Gu> gu = getGuInfos();

        List<PolygonDto> guDto= new ArrayList<>();
        for(Gu g :gu){
            guDto.add(PolygonDto.toDTO(g));
        }

        return guDto;
    }

    private List<Gu> getGuInfos() {
        return guRepository.findAllByOrderByGuName();
    }

    @Override
    public List<PolygonDto> getDongPolygon(Long guCode){
        List<Dong> dong = getDongInfos(guCode);

        List<PolygonDto> dongDto= new ArrayList<>();
        for(Dong d :dong){
            dongDto.add(PolygonDto.toDTO(d));
        }
        return dongDto;
    }

    private List<Dong> getDongInfos(Long guCode) {
        Gu gu = guRepository.getReferenceById(guCode);
        return dongRepository.findByCodeOrderByDongName(gu);
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

    @Override
    public List<RegionInfoDto> getGuInfo() {
        List<Gu> gu = getGuInfos();

        List<RegionInfoDto> guInfos= new ArrayList<>();
        for(Gu g :gu){
            guInfos.add(RegionInfoDto.from(g));
        }

        return guInfos;
    }

    @Override
    public List<RegionInfoDto> getDongInfo(Long code) {
        List<Dong> dong = getDongInfos(code);

        List<RegionInfoDto> dongInfos= new ArrayList<>();
        for(Dong d :dong){
            dongInfos.add(RegionInfoDto.from(d));
        }
        return dongInfos;
    }

    @Override
    public AreaInfoDto getAreaInfo(Long code) {
        Area area = areaRepository.findById(code).orElse(null);
        if(area != null){
            AreaInfoDto areaInfo = AreaInfoDto.toDto(area);
            return areaInfo;
        }
        return null;
    }
}
