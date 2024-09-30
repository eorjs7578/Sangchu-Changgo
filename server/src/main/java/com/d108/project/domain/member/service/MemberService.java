package com.d108.project.domain.member.service;

import com.d108.project.cache.redisEmail.dto.EmailAuthCheckDto;
import com.d108.project.config.util.token.dto.TokenResponseDto;
import com.d108.project.domain.member.dto.MemberLoginDto;
import com.d108.project.domain.member.dto.MemberPasswordChangeDto;
import com.d108.project.domain.member.dto.MemberRegisterDto;
import com.d108.project.domain.member.dto.MemberResponseDto;
import com.d108.project.domain.member.entity.Member;

import java.util.List;

public interface MemberService {

    void registerMember(MemberRegisterDto memberRegisterDto);

    TokenResponseDto loginMember(MemberLoginDto memberLoginDto);

    List<MemberResponseDto> getAllMember();

    void logoutMember(Member member);

    MemberResponseDto getMyInfo(Member member);


    boolean isUsernameDuplicated(String username);

    boolean isNicknameDuplicated(String nickname);

    boolean isEmailDuplicated(String email);

    void changeEmail(Member member, EmailAuthCheckDto emailAuthCheckDto);

    void changePassword(Member member, MemberPasswordChangeDto memberPasswordChangeDto);

    void changeNickname(Member member, String nickname);
}
