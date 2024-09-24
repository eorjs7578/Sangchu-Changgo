package com.d108.project.domain.map.service;

import com.d108.project.cache.redisToken.dto.TokenResponseDto;
import com.d108.project.domain.map.dto.PolygonDto;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;

import java.util.List;

public interface MapService {

    List<PolygonDto> getGuPolygon();

    List<PolygonDto> getDongPolygon(Long code);

    List<PolygonDto> getAreaPolygon(Long code);
}
