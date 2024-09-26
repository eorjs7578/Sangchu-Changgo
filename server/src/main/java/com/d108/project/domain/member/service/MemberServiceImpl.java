package com.d108.project.domain.member.service;


import com.d108.project.cache.redis.RedisUtil;
import com.d108.project.config.util.token.dto.TokenResponseDto;
import com.d108.project.config.util.token.TokenUtil;
import com.d108.project.domain.loginCredential.entity.LoginCredential;
import com.d108.project.domain.loginCredential.repository.LoginCredentialRepository;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import com.d108.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final TokenUtil tokenUtil;
    private final RedisUtil redisUtil;
    private final MemberRepository memberRepository;
    private final LoginCredentialRepository loginCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerMember(MemberRegisterDto memberRegisterDto) {

        // 1. 중복 검증
        String email = memberRegisterDto.getEmail();
        String nickname = memberRegisterDto.getNickname();
        String username = memberRegisterDto.getUsername();

        isNicknameDuplicated(nickname);
        isEmailDuplicated(email);
        isUsernameDuplicated(username);


        String passwordEncode = passwordEncoder.encode(memberRegisterDto.getPassword());

        // Member 생성 로직
        Member member = Member.createMember(memberRegisterDto, passwordEncode);
        // 회원 정보 저장
        memberRepository.save(member);
    }

    // 로그인은 시큐리티에 의해 대체되었습니다.
    @Override
    public TokenResponseDto loginMember(MemberLoginDto memberLoginDto) {
        System.out.println("기존 로그인 로직");
        // 로그인 로직
        LoginCredential loginCredential = loginCredentialRepository.findByUsername(memberLoginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));
        // 비밀번호 확인
        if (passwordEncoder.matches(memberLoginDto.getPassword(), loginCredential.getPassword())) {
            // 비밀번호 검증이 성공 했으면, 토큰을 발급하고 반환
            return tokenUtil.getToken(memberLoginDto.getUsername());
        } else {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }
    }

    // 전체 조회
    @Override
    public List<MemberResponseDto> getAllMember() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(MemberResponseDto::from)// DTO로 변환
                .collect(Collectors.toList());
    }

    // 로그아웃도 시큐리티에 의해 대체되었습니다.
    @Override
    public void logoutMember(Member member) {
        // 레디스에서 엑세스 토큰 삭제
        String redisKey = TokenUtil.REDIS_ACCESS_TOKEN_PREFIX + member.getUsername();
        if (redisUtil.getData(redisKey) != null) {
            redisUtil.deleteData(redisKey);
        }

        // DB에서 리프레시 토큰 삭제
        member.setRefreshToken(null);
        memberRepository.save(member);
        // 시큐리티 내부에서도 삭제
        SecurityContextHolder.clearContext();}

    @Override
    public MemberResponseDto getMyInfo(Member member) {
        return MemberResponseDto.from(member);
    }

    // 유효성 검증 함수
    private boolean isValidate(String email, String password, String nickname) {
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        } else if (!Pattern.matches(NICKNAME_PATTERN, nickname)) {
            throw new IllegalArgumentException("유효하지 않은 닉네임 형식입니다.");
        } else if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            throw new IllegalArgumentException("유효하지 않은 비밀번호 형식입니다.");
        }

        return true;
    }

    public boolean isEmailDuplicated(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        return true;
    }

    public boolean isNicknameDuplicated(String nickname) {
        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        return true;
    }

    public boolean isUsernameDuplicated(String username) {
        if (loginCredentialRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        return true;
    }

}

