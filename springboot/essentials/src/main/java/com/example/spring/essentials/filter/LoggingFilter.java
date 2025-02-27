package com.example.spring.essentials.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@Component : LoggingFilter를 바로 사용할 때 Spiring Container가 흐름을 관리하도록 하기 위해서 사용
public class LoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 (필요시)
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {
        // ** 필수
        // HTTP 방식으로 형변환 : HTTP 용도에 맞게 바로 쓰기 위해서
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // ---> 요청 정보 로깅
        System.out.println("Request URI : " + request.getRequestURI());
        System.out.println("Request Method : " + request.getMethod());
        
        // 필터 체인 : 계속해서 다음 필터 또는 서블릿으로 전달
        filterChain.doFilter(servletRequest, servletResponse);

        // <--- 응답 상태 코드 로깅
        // HTTP 특성 ex) 200 OK
        System.out.println("Response Status : " + response.getStatus());
    }

    @Override
    public void destroy() {
        // 필터 종료 시 처리 (필요시)
    }
}
