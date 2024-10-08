package com.d108.project.interfaces.api;


import com.d108.project.cache.redisEmail.dto.EmailAuthCheckDto;
import com.d108.project.config.util.token.dto.TokenResponseDto;
import com.d108.project.domain.forum.reply.dto.ReplyByMemberIdResponseDto;
import com.d108.project.domain.member.dto.*;
import com.d108.project.domain.member.entity.Member;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/members")
public interface MemberApi {

    @PostMapping("/signup")
    ResponseEntity<Void> registerMember(@RequestBody MemberRegisterDto memberRegisterDto);

    @PostMapping("/login")
    ResponseEntity<TokenResponseDto> loginMember(@RequestBody MemberLoginDto memberLoginDto);

    @GetMapping
    ResponseEntity<List<MemberResponseDto>> getAllMembers();

    @PostMapping("/logout")
    ResponseEntity<Void> logoutMember(@AuthenticationPrincipal Member member);

    @GetMapping("/auth-email")
    ResponseEntity<Void> sendAuthEmail(@RequestParam String email) throws MessagingException;

    @PostMapping("/auth-email")
    ResponseEntity<Void> checkAuthCode(@RequestBody EmailAuthCheckDto emailAuthCheckDto);

    @GetMapping("/replies")
    ResponseEntity<List<ReplyByMemberIdResponseDto>> getMembersReplies(@AuthenticationPrincipal Member member);

    @GetMapping("/my-info")
    ResponseEntity<MemberResponseDto> getMyInfo(@AuthenticationPrincipal Member member);

    //닉네임, 아이디, 이메일 중복 검사
    @GetMapping("/duplicate/email")
    ResponseEntity<String> isEmailDuplicated(@RequestParam String email);
    @GetMapping("/duplicate/username")
    ResponseEntity<String> isUsernameDuplicated(@RequestParam String username);
    @GetMapping("/duplicate/nickname")
    ResponseEntity<String> isNicknameDuplicated(@RequestParam String nickname);

    //닉네임, 이메일, 비밀번호 변경
    @PostMapping("/update/nickname")
    ResponseEntity<Void> changeNickname(@AuthenticationPrincipal Member member, @RequestBody MemberNicknameRequestDto memberNicknameRequestDto);

    @PostMapping("/update/email")
    ResponseEntity<Void> changeEmail(@AuthenticationPrincipal Member member, @RequestBody MemberEmailRequestDto memberEmailRequestDto);

    @PostMapping("/update/password")
    ResponseEntity<Void> changePassword(@AuthenticationPrincipal Member member, @RequestBody MemberPasswordChangeDto memberPasswordChangeDto);

    @PostMapping("/find/check")
    ResponseEntity<Void> checkBeforeFindPassword(@RequestBody MemberCheckRequestDto memberCheckRequestDto);

    @PostMapping("/find/password")
    ResponseEntity<Void> findPassword(@RequestBody MemberFindPasswordDto memberFindPasswordDto);
}
