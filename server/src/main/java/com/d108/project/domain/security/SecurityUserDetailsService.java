package com.d108.project.domain.security;

import com.d108.project.domain.loginCredential.entity.LoginCredential;
import com.d108.project.domain.loginCredential.repository.LoginCredentialRepository;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.repository.MemberRepository;
import com.d108.project.domain.security.dto.SecurityUserDetailsDto;
import com.d108.project.domain.security.dto.SecurityUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final LoginCredentialRepository loginCredentialRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginCredential loginCredential = loginCredentialRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Member member = memberRepository.findById(loginCredential.getId())
                .orElseThrow(() -> new UsernameNotFoundException(username));

        SecurityUserDto securityUserDto = SecurityUserDto.from(member);

        return SecurityUserDetailsDto.from(
                securityUserDto,
                Collections.singleton(
                        new SimpleGrantedAuthority(
                                securityUserDto
                                        .toString()))
        );
    }
}
