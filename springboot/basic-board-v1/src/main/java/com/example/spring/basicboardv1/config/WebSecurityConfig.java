package com.example.spring.basicboardv1.config;

import com.example.spring.basicboardv1.config.security.CustomAuthenticationFailureHandler;
import com.example.spring.basicboardv1.config.security.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig {

    @Bean // 화면에 접속할 때 인증x
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/static/**", "/css/**", "/js/**" // 모든 하위 경로인건 맞지만 파일에 대한 것이 아님, 폴더는 또 따로 잡아줘야 함
                );
    }

    @Bean // 직접 custom > 기존 security화면 안뜸
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            CustomAuthenticationSuccessHandler successHandler,
            CustomAuthenticationFailureHandler failureHandler
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 신뢰할 수 있는 사용자를 사칭해 웹사이트에 원치 않는 명령을 보내는 공격함
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers(
                                        new AntPathRequestMatcher("/join", "POST"),
                                        new AntPathRequestMatcher("/member/join", "GET"),
                                        new AntPathRequestMatcher("/member/login", "GET")
                                ).permitAll()
                                .anyRequest().authenticated() // 허락된 요청 외에는 인증된 방식을 해라
                );

        http
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/member/login") // 너가 생각한 로그인 페이지와 우리가 만든 페이지가 동일하다
                                .loginProcessingUrl("/login") // 실직적으로 인증할 곳 > security한테 위임
                                .successHandler(successHandler)
                                .failureHandler(failureHandler)
                );

        return http.build();
    }

    @Bean // 메서드를 개별로 생성, passwordEncoder만 쓰기 위해서 spring container에 등록
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
