package com.example.spring.basicboardv2.config.jwt;

import com.example.spring.basicboardv2.model.Member;
import com.example.spring.basicboardv2.type.Role;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest // spring boot의 성격을 주입, 없으면 단순히 java code일 뿐
class TokenProviderTest {

    @Autowired // spring boot framework가 제공하는 어노테이션
    TokenProvider tokenProvider;
    @Autowired
    JwtProperties jwtProperties;

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

    @Test
    void makeToken() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date now = new Date();

        Duration duration = Duration.ofSeconds(1);

        Member member = Member.builder()
                .id(0L)
                .userId("test")
                .password("123456")
                .userName("test")
                .role(Role.ROLE_USER)
                .build();


        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + duration.toMillis()))
                .setSubject(member.getUserId())
                .claim("id", member.getId())
                .claim("role", member.getRole().name())
                .claim("userName", member.getUserName())
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();

        System.out.println(token);
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

}