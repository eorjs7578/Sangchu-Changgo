package com.d108.project.config.security.filter;

import com.d108.project.domain.member.dto.MemberLoginDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        // 로그인 엔드포인트로 요청 시 감지
        this.setFilterProcessesUrl("/members/login");
    }

    /**
     * 이 메서드는 사용자가 로그인을 시도할 때 호출됩니다.
     * HTTP요청에서 사용자 이름과 비밀번호를 추출하여 UsernamePasswordAuthenticationToken 객체를 생성하고,
     * 이를 AuthenticationManager에 전달하여 인증을 시도합니다.
     *
     * @param request from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     * redirect as part of a multi-stage authentication process (such as OIDC).
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authRequest;

        try {
            authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }

        // Authentication 객체를 반환한다.
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 이 메서드는 HTTP 요청에서 사용자 이름과 비밀번호를 추출하여
     * UsernamePasswordAuthenticationToken 객체를 생성하는 역할을 한다.
     * HTTP 요청의 입력 스트림에서 JSON 형태의 사용자 이름과 비밀번호를 읽어 UserDTO 객체를 생성하고,
     * 이를 기반으로 UsernamePasswordAuthenticationToken 객체를 생성한다.
     * @param request
     * @throws Exception
     */
    private UsernamePasswordAuthenticationToken getAuthRequest(
            HttpServletRequest request
    ) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

            MemberLoginDto memberLoginDto = objectMapper.readValue(request.getInputStream(), MemberLoginDto.class);
            return new UsernamePasswordAuthenticationToken(memberLoginDto.getUsername(), memberLoginDto.getPassword());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }
}