package com.d108.project.interfaces.api;


import com.d108.project.cache.redisEmail.dto.EmailAuthCheckDto;
import com.d108.project.cache.redisToken.dto.TokenResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyByMemberIdResponseDto;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/members")
public interface MemberApi {

    @PostMapping("/signup")
    ResponseEntity<Void> registerMember(@RequestBody MemberRegisterDto memberRegisterDto);

    @PostMapping("/login")
    ResponseEntity<TokenResponseDto> loginMember(@RequestBody MemberLoginDto memberLoginDto);

    @GetMapping
    ResponseEntity<List<MemberResponseDto>> getAllMembers();

    @PostMapping("/logout")
    ResponseEntity<Void> logoutMember(String username);

    @GetMapping("/auth-email")
    ResponseEntity<Void> sendAuthEmail(@RequestParam String email) throws MessagingException;

    @PostMapping("/auth-email")
    ResponseEntity<Void> checkAuthCode(EmailAuthCheckDto emailAuthCheckDto);

    @GetMapping("/replies")
    ResponseEntity<List<ReplyByMemberIdResponseDto>> getMembersReplies(@RequestParam Long memberId);

    @GetMapping("/my-info")
    ResponseEntity<MemberResponseDto> getMyInfo();
}
