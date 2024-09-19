package com.d108.project.config.security.oauth2.handler;

import com.d108.project.config.security.oauth2.OAuth2Provider;
import com.d108.project.config.security.oauth2.OAuth2UserPrincipal;
import com.d108.project.config.security.oauth2.TokenProvider;
import com.d108.project.config.security.oauth2.repository.OAuth2Repository;
import com.d108.project.config.security.oauth2.unlink.OAuth2UserUnlinkManager;
import com.d108.project.config.security.oauth2.util.CookieUtil;
import com.d108.project.config.security.util.JwtUtil;
import com.d108.project.domain.loginCredential.entity.LoginCredential;
import com.d108.project.domain.member.entity.Member;
import com.d108.project.domain.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static com.d108.project.config.security.oauth2.repository.OAuth2Repository.MODE_PARAM_COOKIE_NAME;
import static com.d108.project.config.security.oauth2.repository.OAuth2Repository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final OAuth2Repository oAuth2Repository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String targetUrl;

        targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String mode = CookieUtil.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);

        if (principal == null) {
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "Login failed")
                    .build().toUriString();
        }

        if ("login".equalsIgnoreCase(mode)) {
            // TODO: DB 저장
            // TODO: 액세스 토큰, 리프레시 토큰 발급
            // TODO: 리프레시 토큰 DB 저장
            log.info("email={}, name={}, nickname={}, accessToken={}",
                    principal.getUserInfo().getEmail(),
                    principal.getUserInfo().getName(),
                    principal.getUserInfo().getNickname(),
                    principal.getUserInfo().getAccessToken()
            );

            String accessToken = jwtUtil.generateToken(authentication.getName(), "accessToken");
            String refreshToken = jwtUtil.generateToken(authentication.getName(), "refreshToken");

            // 존재하지 않는 멤버면 회원 가입 시키고
            Member member = memberRepository.findByUsername(principal.getUsername())
                    .orElse(Member.createMember(
                            principal.getUsername(),
                            passwordEncoder.encode(principal.getUserInfo().getAccessToken()),
                            principal.getNickname(),
                            principal.getEmail()
                    ));
            // 리프레시 토큰 넣어주고
            member.setRefreshToken(refreshToken);
            // 저장
            memberRepository.save(member);

            // 쿠키도 넣어주기
            // TODO: 함수로 변환해서 중복 줄이기
            Cookie accessTokenCookie = new Cookie("access_token", accessToken);
            Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);

            accessTokenCookie.setHttpOnly(true); // JavaScript에서 쿠키에 접근할 수 없도록 설정
            accessTokenCookie.setPath("/"); // 모든 경로에서 쿠키에 접근 가능하도록 설정
            response.addCookie(accessTokenCookie); // 응답에 쿠키 추가

            refreshTokenCookie.setHttpOnly(true); // JavaScript에서 쿠키에 접근할 수 없도록 설정
            refreshTokenCookie.setPath("/"); // 모든 경로에서 쿠키에 접근 가능하도록 설정
            response.addCookie(refreshTokenCookie); // 응답에 쿠키 추가

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("access_token", accessToken)
                    .queryParam("refresh_token", refreshToken)
                    .build().toUriString();

        }

        else if ("unlink".equalsIgnoreCase(mode)) {

            String accessToken = principal.getUserInfo().getAccessToken();
            OAuth2Provider provider = principal.getUserInfo().getProvider();

            // TODO: DB 삭제
            // TODO: 리프레시 토큰 삭제
            oAuth2UserUnlinkManager.unlink(provider, accessToken);

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", "Login failed")
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserPrincipal) {
            return (OAuth2UserPrincipal) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        oAuth2Repository.removeAuthorizationRequestCookies(request, response);
    }
}
