package com.d108.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "창업레츠고 백엔드 API 명세서",
                description = """
                        창업레츠고 백엔드 API 명세입니다.<br>
                        <h2>401: 만료된 토큰 or 유효하지 않은 토큰 사용</h2>
                        <h2>404: 잘못된 입력</h2>
                        <h2>500: 서버에러</h2>
                        """,
                version = "v1"
        ),
        servers = {
                @Server(url = "/", description = "API 서버")
        },
        tags = {
//                @Tag(name = "게시판"),
//                @Tag(name = "상권"),
//                @Tag(name = "상권 평가"),
        }
)
public class SwaggerConfiguration {
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

    //    @Bean
//    public OpenAPI openAPI() {
//        //jwt 토큰 자동추가 설정
//        SecurityScheme securityScheme = new SecurityScheme()
//                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
//                .in(SecurityScheme.In.HEADER).name("Authorization");
//        SecurityRequirement schemaRequirement = new SecurityRequirement().addList("bearerAuth");
//
//        return new OpenAPI()
//                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
//                .security(Arrays.asList(schemaRequirement));
//    }
}
