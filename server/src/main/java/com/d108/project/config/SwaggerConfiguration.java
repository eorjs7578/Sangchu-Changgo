package com.d108.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "창업레츠고 백엔드 API 명세서",
                description = """
                        <h2>401: 만료된 토큰 or 유효하지 않은 토큰 사용</h2>
                        <h2>403: 사용자 권한 없음</h2>
                        <h2>404: 잘못된 입력</h2>
                        <h2>500: 서버에러</h2>
                        """,
                version = "v1"
        ),
        servers = {
//                @Server(url = "/api", description = "API 서버")
                @Server(url = "/", description = "API 서버")
        },
        tags = {
                @Tag(name = "관심 상권"),
                @Tag(name = "관심 프랜차이즈"),
                @Tag(name = "댓글"),
                @Tag(name = "회원 관리"),
                @Tag(name = "게시글"),
        }
)
public class SwaggerConfiguration {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String kakaoAuthorizationUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String naverAuthorizationUri;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String naverTokenUri;


//    private final Environment environment;
//
//    @Autowired
//    public SwaggerConfiguration(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Bean
//    public ModelResolver modelResolver(ObjectMapper objectMapper) {
//        return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
//    }
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes("kakao_oauth2", createOAuthSecurityScheme("kakao"))
//                        .addSecuritySchemes("naver_oauth2", createOAuthSecurityScheme("naver"))
//                );
//    }
//
//    private SecurityScheme createOAuthSecurityScheme(String provider) {
//        return new SecurityScheme()
//                .type(SecurityScheme.Type.OAUTH2)
//                .flows(new OAuthFlows()
//                        .authorizationCode(new OAuthFlow()
//                                .authorizationUrl(environment.getProperty("springdoc.oAuthFlow.authorizationUrl-" + provider))
//                                .tokenUrl(environment.getProperty("springdoc.oAuthFlow.tokenUrl-" + provider))
//                                .scopes(createScopes(provider))
//                        )
//                );
//    }
//
//    private Scopes createScopes(String provider) {
//        Scopes scopes = new Scopes();
//        String scopesProperty = "springdoc.oAuthScopes." + provider;
//        List<String> scopesList = environment.getProperty(scopesProperty, List.class);
//        if (scopesList != null) {
//            for (String scope : scopesList) {
//                scopes.addString(scope, "Access to " + scope);
//            }
//        }
//        return scopes;
//    }

    // 카카오만은 되던 코드

    /**
     * 이 코드는 ObjectMapper의 네이밍 전략을 snake_case로 설정한 후,
     * 해당 ObjectMapper를 사용하는 **ModelResolver**를 Spring 빈으로 등록합니다.
     * 이렇게 설정하면, Swagger나 다른 API 문서화 도구에서 snake_case를 사용하여 모델의 필드 이름을 처리하게 됩니다.
     * @param objectMapper
     * @return
     */
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("kakao_oauth2", createOAuthSecurityScheme("kakao"))
                        .addSecuritySchemes("naver_oauth2", createOAuthSecurityScheme("naver"))
                );
    }

    private SecurityScheme createOAuthSecurityScheme(String provider) {
        String authorizationUrl = provider.equals("kakao") ? kakaoAuthorizationUri : naverAuthorizationUri;
        String tokenUrl = provider.equals("kakao") ? kakaoTokenUri : naverTokenUri;

        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .authorizationCode(new OAuthFlow()
                                .authorizationUrl(authorizationUrl)
                                .tokenUrl(tokenUrl)
                                .scopes(new Scopes()
                                        .addString("profile_nickname", "Access to profile nickname")
                                        .addString("account_email", "Access to account email")
                                )
                        )
                );
    }

//    private SecurityScheme createOAuthSecurityScheme(String provider) {
//        return new SecurityScheme()
//                .type(SecurityScheme.Type.OAUTH2)
//                .flows(new OAuthFlows()
//                        .authorizationCode(new OAuthFlow()
//                                .authorizationUrl(environment.getProperty("springdoc.oAuthFlow.authorizationUrl-" + provider))
//                                .tokenUrl(environment.getProperty("springdoc.oAuthFlow.tokenUrl-" + provider))
//                                .scopes(new Scopes()
//                                        .addString("profile_nickname", "Access to profile nickname")
//                                        .addString("account_email", "Access to account email")
//                                )
//                        )
//                );
//    }




    // 예전에 jwt 되던 코드
//    @Bean
//    public OpenAPI customOpenAPI() {
//        final String jwtSchemeName = "jwtAuth";
//        final String naverSchemeName = "naver";
//        final String kakaoSchemeName = "kakao";
//
//        // JWT 토큰을 위한 SecurityScheme 정의
//        SecurityScheme jwtScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("bearer")
//                .bearerFormat("JWT")
//                .in(SecurityScheme.In.HEADER)
//                .name("Authorization");
//
////         Naver 소셜 로그인을 위한 SecurityScheme 정의
//        SecurityScheme naverScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.OAUTH2)
//                .flows(new OAuthFlows()
//                        .authorizationCode(new OAuthFlow()
//                                .authorizationUrl(naverAuthorizationUri)
//                                .tokenUrl("https://nid.naver.com/oauth2.0/token")
//                                .scopes(new Scopes()
//                                        .addString("name", "이름")
//                                        .addString("email", "이메일")
//                                        .addString("nickname", "닉네임")
//                                )
//                        )
//                );
//
////         Kakao 소셜 로그인을 위한 SecurityScheme 정의
//        SecurityScheme kakaoScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.OAUTH2)
//                .flows(new OAuthFlows()
//                        .authorizationCode(new OAuthFlow()
//                                .authorizationUrl(kakaoAuthorizationUri)
//                                .tokenUrl("https://kauth.kakao.com/oauth/token")
//                                .scopes(new Scopes()
//                                        .addString("profile_nickname", "닉네임")
//                                        .addString("account_email", "이메일")
//                                )
//                        )
//                );
//
////        SecurityRequirement 정의
//        SecurityRequirement securityRequirement = new SecurityRequirement()
//                .addList(jwtSchemeName)
//                .addList(naverSchemeName)
//                .addList(kakaoSchemeName);
//
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes(jwtSchemeName, jwtScheme)
//                        .addSecuritySchemes(naverSchemeName, naverScheme)
//                        .addSecuritySchemes(kakaoSchemeName, kakaoScheme)
//                )
//                .security(Collections.singletonList(securityRequirement));
//    }
}
