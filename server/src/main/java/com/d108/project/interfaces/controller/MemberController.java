package com.d108.project.interfaces.controller;

import com.d108.project.domain.member.MemberService;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import com.d108.project.interfaces.api.MemberApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public ResponseEntity<MemberResponseDto> registerMember(MemberRegisterDto memberRegisterDto) {
        memberService.registerMember(memberRegisterDto);

        MemberLoginDto memberLoginDto = MemberLoginDto.builder()
                .username(memberRegisterDto.getUsername())
                .password(memberRegisterDto.getPassword())
                .build();
        // 바로 로그인
        return ResponseEntity.ok(memberService.loginMember(memberLoginDto));
    }

    @Override
    public ResponseEntity<MemberResponseDto> loginMember(MemberLoginDto memberLoginDto) {
        return ResponseEntity.ok(memberService.loginMember(memberLoginDto));
    }

    @Override
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMember());
    }
}
