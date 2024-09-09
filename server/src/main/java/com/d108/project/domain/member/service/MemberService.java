package com.d108.project.domain.member.service;

import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;

import java.util.List;

public interface MemberService {

    void registerMember(MemberRegisterDto memberRegisterDto);

    MemberResponseDto loginMember(MemberLoginDto memberLoginDto);

    List<MemberResponseDto> getAllMember();
}
