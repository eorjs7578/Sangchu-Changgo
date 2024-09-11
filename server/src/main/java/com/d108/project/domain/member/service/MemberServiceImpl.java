package com.d108.project.domain.member.service;


import com.d108.project.cache.redis.RedisUtil;
import com.d108.project.cache.redisToken.dto.TokenResponseDto;
import com.d108.project.config.security.util.JwtUtil;
import com.d108.project.domain.loginCredential.entity.LoginCredential;
import com.d108.project.domain.loginCredential.repository.LoginCredentialRepository;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import com.d108.project.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final MemberRepository memberRepository;
    private final LoginCredentialRepository loginCredentialRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    // 이메일 정규표현식(aaa@a~)
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9]+@(.+)$";
    // 비밀번호 정규표현식(8자 이상, 숫자 1, 특수문자 1 포함)
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?=\\S+$).{8,}$";
    // 닉네임 정규표현식
    private static final String NICKNAME_PATTERN = "^(?!.*[ㄱ-ㅎㅏ-ㅣ])[A-Za-z0-9가-힣]{1,10}$";

    @Override
    @Transactional
    public void registerMember(MemberRegisterDto memberRegisterDto) {
        // 회원가입 로직
        // 이거 프론트에서만 하면 안되나..
        String email = memberRegisterDto.getEmail();
        String password = memberRegisterDto.getPassword();
        String nickname = memberRegisterDto.getNickname();
        String username = memberRegisterDto.getUsername();

        // 1. 정규 표현식을 통한 유효성 검증
        isValidate(email, password, nickname);
        // 2. 중복 검증
        isNicknameDuplicated(nickname);
        isEmailDuplicated(email);
        isUsernameDuplicated(username);
        // 3. 이메일 중복 검사는 따로 하고 프론트에서 고정 시키면 될듯


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
            return jwtUtil.getToken(memberLoginDto.getUsername());
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

    // 로그아웃 시 레디스에서 삭제
    @Override
    public void logoutMember(String username) {
        if (redisUtil.getData(username) != null) {
            redisUtil.deleteData(username);
        }
    }

    // 유효성 검증 함수
    private void isValidate(String email, String password, String nickname) {
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        } else if (!Pattern.matches(NICKNAME_PATTERN, nickname)) {
            throw new IllegalArgumentException("유효하지 않은 닉네임 형식입니다.");
        } else if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            throw new IllegalArgumentException("유효하지 않은 비밀번호 형식입니다.");
        }
    }

    private void isEmailDuplicated(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
    }

    private void isNicknameDuplicated(String nickname) {
        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
    }

    private void isUsernameDuplicated(String username) {
        if (loginCredentialRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }
}
