package com.d108.project.interfaces.controller;

import com.d108.project.cache.redisEmail.RedisEmailService;
import com.d108.project.cache.redisEmail.dto.EmailAuthCheckDto;
import com.d108.project.config.util.token.dto.TokenResponseDto;
import com.d108.project.domain.forum.reply.ReplyService;
import com.d108.project.domain.forum.reply.dto.ReplyByMemberIdResponseDto;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.service.MemberService;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import com.d108.project.interfaces.api.MemberApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "회원 관리")
@RestController
@RequiredArgsConstructor
public class MemberController implements MemberApi {

    private final MemberService memberService;
    private final ReplyService replyService;
    private final RedisEmailService redisEmailService;

    @Operation(summary = "회원 가입", description = "")
    @Override
    public ResponseEntity<Void> registerMember(MemberRegisterDto memberRegisterDto) {
        memberService.registerMember(memberRegisterDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그인", description =
            "<p>security 안으로 말려들어갔을 듯</p>" +
            "<p>요청 보내는 방식은 동일함</p>")
    @Override
    public ResponseEntity<TokenResponseDto> loginMember(MemberLoginDto memberLoginDto) {
        return ResponseEntity.ok(memberService.loginMember(memberLoginDto));
    }

    @Operation(summary = "전체 유저 조회", description = "테스트 용도로 놔둔거라 나중에 삭제하거나 어드민 전용으로 바꿔야 할 듯")
    @Override
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMember());
    }

    @Operation(summary = "로그아웃", description =
                    "<p>시큐리티에서 인증 정보를 받아옴</p>" +
                    "<p>로그인 한 유저인지 확인해야함</p>" +
                    "<p>내부에서 쿠키 지우는 로직 있음. 프론트에서도 확인 필요</p>"
    )
    @Override
    public ResponseEntity<Void> logoutMember(Member member) {
        memberService.logoutMember(member);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "인증 메일 발송", description = "레디스에 인증번호 저장")
    @Override
    public ResponseEntity<Void> sendAuthEmail(String email) throws MessagingException {
        redisEmailService.sendEmail(email);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "인증 번호 확인", description = "레디스에서 인증번호 비교")
    @Override
    public ResponseEntity<Void> checkAuthCode(EmailAuthCheckDto emailAuthCheckDto) {
        redisEmailService.checkAuthCode(emailAuthCheckDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저의 댓글 전체 조회", description =
                    "<p>시큐리티에서 인증 정보를 받아옴</p>" +
                    "<p>로그인 한 유저인지 확인해야함</p>"
    )
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
        System.out.println(member);
        return ResponseEntity.ok(memberService.getMyInfo(member));
    }

    @Operation(summary = "이메일 중복 확인")
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
