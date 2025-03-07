package com.example.spring.v2practice.config.jwt;

import com.example.spring.v2practice.model.Member;
import com.example.spring.v2practice.type.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return makeToken(
                member,
                new Date(now.getTime() + expiredAt.toMillis())
        );
    }

    private String makeToken(Member member, Date expired) {

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expired)
                .setSubject(member.getUserId())
                .claim("id", member.getId())
                .claim("userName", member.getUserName())
                .claim("role", member.getRole())
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Member getTokenDetails(String token) {
        Claims claims = getClaims(token);

        return Member.builder()
                .id(claims.get("id", Long.class))
                .userId(claims.getSubject())
                .userName(claims.get("userName", String.class))
                .role(
                        Role.valueOf(claims.get("role", String.class))
                )
                .build();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(claims.get("role", String.class))
        );

        UserDetails userDetails = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    public int validToken(String token) {
        try {
            getClaims(token);

            return 1;
        } catch (ExpiredJwtException e) {
            log.info("token expired");
            return 2;
        } catch (Exception e) {
            System.out.println("Token 복호화 에러 : " + e.getMessage());
            return 3;
        }
    }

}
