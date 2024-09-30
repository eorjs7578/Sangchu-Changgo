package com.d108.project.config.security;

import com.d108.project.config.WhiteListConfiguration;
import com.d108.project.config.security.filter.CustomAuthenticationFilter;
import com.d108.project.config.security.filter.JwtAuthorizationFilter;
import com.d108.project.config.security.handler.CustomAuthFailureHandler;
import com.d108.project.config.security.handler.CustomAuthSuccessHandler;
import com.d108.project.config.security.oauth2.OAuth2UserService;
import com.d108.project.config.security.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.d108.project.config.security.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.d108.project.config.security.oauth2.repository.OAuth2Repository;
import com.d108.project.config.util.token.TokenUtil;
import com.d108.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration {

    private final WhiteListConfiguration whiteListConfiguration;
    private final OAuth2Repository oAuth2Repository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final OAuth2UserService oAuth2UserService;

    // 정적 자원에 대한 보안 적용 해제
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * Spring Security 설정을 정의하는 메서드.
     * <ul>
     *     <li>CSRF 방지 기능 비활성화</li>
     *     <li>CORS 설정 적용</li>
     *     <li>정적 파일 및 화이트리스트 경로 접근 허용</li>
     *     <li>나머지 요청은 인증된 사용자만 접근 허용</li>
     *     <li>헤더의 JWT 토큰을 추출하고 검증하는 `JwtAuthorizationFilter` 적용</li>
     *     <li>세션을 상태 없이 관리</li>
     *     <li>사용자 이름과 비밀번호 인증을 위한 `CustomAuthenticationFilter` 적용</li>
     * </ul>
     *
     * @param http Spring Security의 HttpSecurity 객체
     * @param customAuthenticationFilter 사용자 정의 인증 필터
     * @param jwtAuthorizationFilter JWT 인증 필터
     * @return 구성된 SecurityFilterChain 객체
     * @throws Exception 설정 중 발생할 수 있는 예외
     */
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            CustomAuthenticationFilter customAuthenticationFilter,
            JwtAuthorizationFilter jwtAuthorizationFilter
    ) throws Exception {
        return http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // 화이트리스트에 대한 권한 허용
                        .requestMatchers(HttpMethod.GET, whiteListConfiguration.getWhiteListForGet()).permitAll()
                        .requestMatchers(whiteListConfiguration.getWhiteList()).permitAll()
                        .requestMatchers(whiteListConfiguration.getWhiteListForSwagger()).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthorizationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(configure ->
                        configure.authorizationEndpoint(
                                        config -> config
                                                .baseUri("/api/oauth2/authorization")
                                                .authorizationRequestRepository(oAuth2Repository))
                                .userInfoEndpoint(config -> config.userService(oAuth2UserService))
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                                .failureHandler(oAuth2AuthenticationFailureHandler)
                )
                .logout(logout -> logout
                                // 로그아웃 페이지에 대한 설정
                                .logoutUrl("/api/members/logout")
                                // 로그아웃 하면서 인증 정보를 삭제하고
                                .clearAuthentication(true)
                                // 쿠키를 삭제함
                                .deleteCookies("access_token", "refresh_token", "JSESSIONID")
                                // 세션 무효화
                                .invalidateHttpSession(true)
//                        // 로그아웃에 성공하면 여기로 보냄 (메인으로 리디렉션하는 코드 만들어도 될듯)
//                        .logoutSuccessUrl("/")
                )
                .build();
    }

    /**
     * 1. 커스텀을 수행한 '인증' 필터로 접근 URL, 데이터 전달방식(form) 등 인증 과정 및 인증 후 처리에 대한 설정을 구성하는 메서드다.
     * 이 메서드는 사용자 정의 인증 필터를 생성한다. 이 필터는 로그인 요청을 처리하고, 인증 성공/실패 핸들러를 설정한다.
     *
     * @return CustomAuthenticationFilter
     */
    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(
            AuthenticationManager authenticationManager,
            CustomAuthSuccessHandler customAuthSuccessHandler,
            CustomAuthFailureHandler customAuthFailureHandler
    ) {
        System.out.println("in customAuthenticationFilter");
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager);
        // "/members/login" 엔드포인트로 들어오는 요청을 CustomAuthenticationFilter에서 처리하도록 지정한다.
        customAuthenticationFilter.setFilterProcessesUrl("/api/members/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthSuccessHandler);    // '인증' 성공 시 해당 핸들러로 처리를 전가한다.
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthFailureHandler);    // '인증' 실패 시 해당 핸들러로 처리를 전가한다.
//        System.out.println("뭥미");
        customAuthenticationFilter.afterPropertiesSet();
//        System.out.println("완");
        return customAuthenticationFilter;
    }

    /**
     * 2. authenticate 의 인증 메서드를 제공하는 매니저로, 'Provider'의 인터페이스를 의미한다.
     * 이 메서드는 인증 매니저를 생성한다. 인증 매니저는 인증 과정을 처리하는 역할을 한다.
     * 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
     */
    @Bean
    public AuthenticationManager authenticationManager(CustomAuthenticationProvider customAuthenticationProvider) {
        return new ProviderManager(Collections.singletonList(customAuthenticationProvider));
    }

    /**
     * 3. '인증' 제공자로 사용자의 이름과 비밀번호가 요구된다.
     * 이 메서드는 사용자 정의 인증 제공자를 생성한다. 인증 제공자는 사용자 이름과 비밀번호를 사용하여 인증을 수행한다.
     * 과정: CustomAuthenticationFilter → AuthenticationManager(interface) → CustomAuthenticationProvider(implements)
     */
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(UserDetailsService userDetailsService) {
        return new CustomAuthenticationProvider(
                userDetailsService
        );
    }

    /**
     * 4. Spring Security 기반의 사용자의 정보가 맞을 경우 수행이 되며 결과값을 리턴해주는 Handler
     * customLoginSuccessHandler: 이 메서드는 인증 성공 핸들러를 생성한다. 인증 성공 핸들러는 인증 성공시 수행할 작업을 정의한다.
     */
    @Bean
    public CustomAuthSuccessHandler customLoginSuccessHandler(
            TokenUtil tokenUtil
    ) {
        return new CustomAuthSuccessHandler(tokenUtil);
    }

    /**
     * 5. Spring Security 기반의 사용자의 정보가 맞지 않을 경우 수행이 되며 결과값을 리턴해주는 Handler
     * customLoginFailureHandler: 이 메서드는 인증 실패 핸들러를 생성한다. 인증 실패 핸들러는 인증 실패시 수행할 작업을 정의한다.
     */
    @Bean
    public CustomAuthFailureHandler customLoginFailureHandler() {
        return new CustomAuthFailureHandler();
    }

    /**
     * "JWT 토큰을 통하여서 사용자를 인증한다." -> 이 메서드는 JWT 인증 필터를 생성한다.
     * JWT 인증 필터는 요청 헤더의 JWT 토큰을 검증하고, 토큰이 유효하면 토큰에서 사용자의 정보와 권한을 추출하여 SecurityContext에 저장한다.
     */
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(
            TokenUtil tokenUtil,
            WhiteListConfiguration whiteListConfiguration
    ) {
        return new JwtAuthorizationFilter(tokenUtil, whiteListConfiguration);
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Collections.singletonList("*"));
//        configuration.setAllowedHeaders(Collections.singletonList("*"));
//        configuration.addAllowedOrigin("http://localhost:5173");
//        configuration.addAllowedOrigin("https://localhost:5173");
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}

