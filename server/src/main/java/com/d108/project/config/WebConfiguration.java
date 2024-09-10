package com.d108.project.config;

import com.d108.project.config.security.interceptor.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final JwtTokenInterceptor jwtTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인터셉터를 설정해 토큰의 유효성을 검증
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/**") // 모든 URL에 대해 JWT 토큰 검사를 적용
                // 예외 페이지 설정하기
                .excludePathPatterns(
                        "/main",
                        "/posts/*,","/posts",
                        "/members/login", "/members/signup",
                        "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/error"
                );
    }
    // 백엔드 서버의 정적 리소스 위치 경로
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/",
            "classpath:/META-INF/resources/",
    };

    // 프론트 메인 페이지 랜딩 시 보낼 위치 설정 (지금은 main으로 설정)
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/main");
        // 최우선으로 설정
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    // 정적 리소스에 대한 핸들러 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
}
