package com.d108.project.config.security;

import com.d108.project.domain.security.dto.SecurityUserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Authenticating {}", authentication);

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // AuthenticationFilter에서 생성된 토큰으로부터 로그인 정보를 조회
        String loginId = token.getName();
        String userPassword = (String) token.getCredentials();

        // Spring security - UserDetailsService를 통해 DB에서 username으로 사용자 조회
        SecurityUserDetailsDto securityUserDetailsDto = (SecurityUserDetailsDto) userDetailsService.loadUserByUsername(loginId);

        // 대소문자를 구분하는 matches() 메서드를 통해 DB에서 username으로 사용자 조회
        if (!bCryptPasswordEncoder().matches(userPassword, securityUserDetailsDto.getSecurityUserDto().getPassword())) {
            throw new BadCredentialsException(securityUserDetailsDto.getUsername() + "Invalid password");
        }
        // 인증에 성공하면 인증된 사용자의 정보와 권한을 담은 새로운 UsernamePasswordAuthenticationToken 반환
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(securityUserDetailsDto, userPassword, securityUserDetailsDto.getAuthorities());
        return authToken;
    }

    /**
     * 이 AuthenticationProvider가 특정 Authentication타입을 지원하는지 여부를 반환한다.
     * 여기서는 UsernamePasswordAuthenticationToken 클래스를 지원한다.
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}