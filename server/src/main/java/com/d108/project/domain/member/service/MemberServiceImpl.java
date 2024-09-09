package com.d108.project.domain.member.service;


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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LoginCredentialRepository loginCredentialRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void registerMember (MemberRegisterDto memberRegisterDto) {
        // 회원가입 로직
        String passwordEncode = passwordEncoder.encode(memberRegisterDto.getPassword());
        LoginCredential loginCredential = LoginCredential.builder()
                .username(memberRegisterDto.getUsername())
                // 비밀번호 암호화
                .password(passwordEncode)
                .build();

        // Member 생성 로직
        Member member = Member.createMember(memberRegisterDto, passwordEncode);

        // 자격 증명 저장 (상속으로 인해서 자동으로 저장)
//        loginCredentialRepository.save(loginCredential);

        // 회원 정보 저장
        memberRepository.save(member);
    }

    @Override
    public MemberResponseDto loginMember(MemberLoginDto memberLoginDto) {
        // 로그인 로직
        LoginCredential loginCredential = loginCredentialRepository.findByUsername(memberLoginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."));

        Member member = memberRepository.findById(loginCredential.getId()).orElseThrow(() -> new IllegalArgumentException("아이디가 없습니다."));

        // 비밀번호 확인
        if (passwordEncoder.matches(memberLoginDto.getPassword(), loginCredential.getPassword())) {
            return MemberResponseDto.builder()
                    .username(loginCredential.getUsername())
                    .nickname(member.getNickname())
                    .build();
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
}
