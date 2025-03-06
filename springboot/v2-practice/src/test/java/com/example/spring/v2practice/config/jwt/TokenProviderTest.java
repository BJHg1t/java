package com.example.spring.v2practice.config.jwt;

import com.example.spring.v2practice.model.Member;
import com.example.spring.v2practice.type.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    TokenProvider tokenProvider;

    @Test
    void testGenToken() {
        Member member = Member.builder()
                .id(0L)
                .userId("test")
                .password("1234")
                .userName("test")
                .role(Role.ROLE_USER)
                .build();

        Duration duration = Duration.ofHours(1);

        String token = tokenProvider.generateToken(member, duration);

        System.out.println(token);
        assertNotNull(token);
    }
}