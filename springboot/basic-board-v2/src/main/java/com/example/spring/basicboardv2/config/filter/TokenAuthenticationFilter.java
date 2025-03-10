package com.example.spring.basicboardv2.config.filter;

import com.example.spring.basicboardv2.config.jwt.TokenProvider;
import com.example.spring.basicboardv2.model.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 요청할 때마다 한번씩 작동하는 필터
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer "; // 토큰 종류가 많아서 명시해주는 것, 공백까지 길이 : 7

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        log.info("Request URI: {}", requestURI);
        if ("/refresh-token".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = resolveToken(request); // access token 검증
        if (token != null && tokenProvider.validToken(token) == 1) {
            // 토큰이 유효할 경우, 인증 정보를 설정
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 정보를 넣는 것, 인증 객체는 응답이 끝나면 사라짐

            Member member = tokenProvider.getTokenDetails(token);
            request.setAttribute("member", member);
        } else if (token != null && tokenProvider.validToken(token) == 2) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return; // 더 이상 진행하지 않음
        }

        filterChain.doFilter(request, response); // 다음 단계로 진행시키는 것 : MemberApiController /user/info
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length()); // 앞부분 잘라서 token에 대한 정보만 받음, 인증할 때 "Bearer " 때문에 문제 생김
        }

        return null;
    }
}
