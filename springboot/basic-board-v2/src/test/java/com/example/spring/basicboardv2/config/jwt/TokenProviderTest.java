package com.example.spring.basicboardv2.config.jwt;

import com.example.spring.basicboardv2.model.Member;
import com.example.spring.basicboardv2.type.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest // spring boot의 성격을 주입, 없으면 단순히 java code일 뿐
class TokenProviderTest {

    @Autowired // spring boot framework가 제공하는 어노테이션
    TokenProvider tokenProvider;

    @Test
    void 토큰생성_테스트() {
        // given
        Member member = Member.builder()
                .id(0L)
                .userId("test")
                .password("1234")
                .userName("test")
                .role(Role.ROLE_USER)
                .build();

        Duration duration = Duration.ofHours(1); // 1시간 짜리
        // when
        String token = tokenProvider.generateToken(member, duration);
        // then
        System.out.println(token);
        assertNotNull(token); // null이면 안됨, Assertions.assertNotNull(token) 에서 alt + enter
    }

}