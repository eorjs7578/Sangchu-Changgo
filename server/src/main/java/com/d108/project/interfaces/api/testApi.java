package com.d108.project.interfaces.api;


import com.d108.project.domain.member.dto.MemberResponseDto;
import com.d108.project.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/test")
public interface testApi {

    @GetMapping
    ResponseEntity<MemberResponseDto> testCode(@AuthenticationPrincipal Member member);
}
