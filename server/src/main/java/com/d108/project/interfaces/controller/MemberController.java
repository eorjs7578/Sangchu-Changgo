package com.d108.project.interfaces.controller;

import com.d108.project.cache.redisEmail.RedisEmailService;
import com.d108.project.cache.redisEmail.dto.EmailAuthCheckDto;
import com.d108.project.cache.redisToken.dto.TokenResponseDto;
import com.d108.project.domain.forum.reply.ReplyService;
import com.d108.project.domain.forum.reply.dto.ReplyByMemberIdResponseDto;
import com.d108.project.domain.member.service.MemberService;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import com.d108.project.interfaces.api.MemberApi;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;
    private final ReplyService replyService;
    private final RedisEmailService redisEmailService;

    @Override
    public ResponseEntity<Void> registerMember(MemberRegisterDto memberRegisterDto) {
        memberService.registerMember(memberRegisterDto);
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

    @Override
    public ResponseEntity<Void> logoutMember(String username) {
        memberService.logoutMember(username);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> sendAuthEmail(String email) throws MessagingException {
        redisEmailService.sendEmail(email);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> checkAuthCode(EmailAuthCheckDto emailAuthCheckDto) {
        redisEmailService.checkAuthCode(emailAuthCheckDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ReplyByMemberIdResponseDto>> getMembersReplies(Member member) {
        return ResponseEntity.ok(replyService.getAllReplyByMemberId(member.getId()));
    }
    @Operation(summary = "내 정보 조회", description =
                    "<p>시큐리티에서 인증 정보를 받아옴</p>" +
                    "<p>로그인 한 유저인지 확인해야함</p>"
    )
    @Override
    public ResponseEntity<MemberResponseDto> getMyInfo(Member member) {
        return ResponseEntity.ok(memberService.getMyInfo(member));
    }

    @Override
    public ResponseEntity<String> isEmailDuplicated(String email) {
        if (memberService.isEmailDuplicated(email)) {
            return ResponseEntity.ok("사용할 수 있는 이메일 입니다.");
        }
        return ResponseEntity.badRequest().body("중복된 이메일 입니다.");
    }
    
    @Operation(summary = "닉네임 중복 확인")
    @Override
    public ResponseEntity<String> isNicknameDuplicated(String nickname) {
        if (memberService.isNicknameDuplicated(nickname)) {
            return ResponseEntity.ok("사용할 수 있는 닉네임 입니다.");
        }
        return ResponseEntity.badRequest().body("중복된 닉네임 입니다.");
    }

    @Operation(summary = "아이디 중복 확인")
    @Override
    public ResponseEntity<String> isUsernameDuplicated(String username) {
        if (memberService.isUsernameDuplicated("사용할 수 있는 아이디 입니다.")) {
            return ResponseEntity.ok("사용할 수 있는 아이디 입니다.");
        }
        return ResponseEntity.badRequest().body("중복된 아이디입니다.");
    }
}
