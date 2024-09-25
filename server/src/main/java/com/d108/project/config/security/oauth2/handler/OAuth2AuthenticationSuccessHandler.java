package com.d108.project.config.security.oauth2.handler;

import com.d108.project.config.util.token.dto.TokenResponseDto;
import com.d108.project.config.security.oauth2.OAuth2Provider;
import com.d108.project.config.security.oauth2.OAuth2UserPrincipal;
import com.d108.project.config.security.oauth2.repository.OAuth2Repository;
import com.d108.project.config.security.oauth2.unlink.OAuth2UserUnlinkManager;
import com.d108.project.config.util.CookieUtil;
import com.d108.project.config.util.token.TokenUtil;
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
    private final TokenUtil tokenUtil;
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

        if ("login".equalsIgnoreCase(mode)) {
            // 존재하지 않는 멤버면 회원 가입 시키고
            Member member = memberRepository.findByUsername(principal.getUsername())
                    .orElseGet(() ->
                            memberRepository.save(Member.createMember(
                                    principal.getUsername(),
                                    passwordEncoder.encode(principal.getUserInfo().getAccessToken()),
                                    principal.getNickname(),
                                    principal.getEmail()
                            ))
                    );

            // 토큰 생성
            TokenResponseDto tokenResponseDto = tokenUtil.getToken(member.getUsername());
            String accessToken = tokenResponseDto.getAccessToken();
            String refreshToken = tokenResponseDto.getRefreshToken();

            // 리프레시 토큰 넣어주고
            member.setRefreshToken(refreshToken);
            // 저장
            memberRepository.save(member);
            // 쿠키에 푸시하고
            tokenUtil.pushTokenOnResponse(response, accessToken, refreshToken);

            // 리턴
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();
        }
        // 언링크는 후순위
        else if ("unlink".equalsIgnoreCase(mode)) {

            String accessToken = principal.getUserInfo().getAccessToken();
            OAuth2Provider provider = principal.getUserInfo().getProvider();

            // DB 삭제
            // 리프레시 토큰 삭제
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
