package com.d108.project.interfaces.controller;

import com.d108.project.domain.member.dto.MemberResponseDto;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.interfaces.api.testApi;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController implements testApi {
    // AuthenticationPrincipal 테스트용
    @Override
    public ResponseEntity<MemberResponseDto> testCode(@AuthenticationPrincipal Member member) {
        System.out.println(member);
        return ResponseEntity.ok(MemberResponseDto.from(member));
    }
}
