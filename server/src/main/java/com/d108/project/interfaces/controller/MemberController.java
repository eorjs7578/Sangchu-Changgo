package com.d108.project.interfaces.controller;

import com.d108.project.cache.redisToken.dto.TokenResponseDto;
import com.d108.project.domain.member.service.MemberService;
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
    public ResponseEntity<Void> registerMember(MemberRegisterDto memberRegisterDto) {
        memberService.registerMember(memberRegisterDto);
        // 바로 로그인하지말자
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TokenResponseDto> loginMember(MemberLoginDto memberLoginDto) {
        return ResponseEntity.ok(memberService.loginMember(memberLoginDto));
    }

    @Override
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMember());
    }
}
