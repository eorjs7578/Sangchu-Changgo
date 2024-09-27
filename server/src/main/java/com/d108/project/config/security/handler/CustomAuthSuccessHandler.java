package com.d108.project.config.security.handler;

import com.d108.project.config.util.token.dto.TokenResponseDto;
import com.d108.project.config.util.token.TokenUtil;
import com.d108.project.domain.loginCredential.entity.LoginCredential;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenUtil tokenUtil;
    /**
     * 로그인 성공
     * 토큰 쥐어주고 보내기
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        System.out.println("SuccessHandler: " + authentication.getPrincipal());
        // 사용자와 관련된 정보를 모두 조회한다.
        String username = ((LoginCredential) authentication.getPrincipal()).getUsername();

        // 토큰 생성
        TokenResponseDto tokenResponseDto = tokenUtil.getToken(username);
        // 프론트 파트
        String accessToken = tokenResponseDto.getAccessToken();
        String refreshToken = tokenResponseDto.getRefreshToken();
        // 쿠키에 푸시
        tokenUtil.pushTokenOnResponse(response, accessToken, refreshToken);

        // 구성한 응답값을 전달한다.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");


        // 조회한 데이터를 JSONObject 형태로 파싱한다.
        JSONObject jsonObject;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("resultCode", 200);
        responseMap.put("failMessage", null);
        jsonObject = new JSONObject(responseMap);
        jsonObject.put("access_token", accessToken);
        jsonObject.put("refresh_token", refreshToken);

        try (PrintWriter printWriter = response.getWriter()){
            printWriter.print(jsonObject); // 최종 저장된 '사용자 정보', '사이트 정보'를 Front에 전달
            printWriter.flush();
        }
    }

}
