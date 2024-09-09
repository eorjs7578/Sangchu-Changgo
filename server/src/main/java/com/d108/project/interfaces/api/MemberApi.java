package com.d108.project.interfaces.api;


import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/members")
public interface MemberApi {

    @PostMapping("/signup")
    ResponseEntity<MemberResponseDto> registerMember(@RequestBody MemberRegisterDto memberRegisterDto);

    @PostMapping("/login")
    ResponseEntity<MemberResponseDto> loginMember(@RequestBody MemberLoginDto memberLoginDto);

    @GetMapping
    ResponseEntity<List<MemberResponseDto>> getAllMembers();
}
