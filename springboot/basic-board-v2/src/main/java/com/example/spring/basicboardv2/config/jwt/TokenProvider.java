package com.example.spring.basicboardv2.config.jwt;

import com.example.spring.basicboardv2.model.Member;
import com.example.spring.basicboardv2.type.Role;
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
                new Date( now.getTime() + expiredAt.toMillis() ) // 지금 시점으로부터 얼마나 유효할 것인가
        );
    }

    // 만료시간에 따른 access 토큰 / refresh 토큰
    private String makeToken(Member member, Date expired) {

        // 왠만하면 local~ 쓰는게 좋음
        Date now = new Date();

        // 토큰 만들기
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now) // 발행시간
                .setExpiration(expired)
                .setSubject(member.getUserId())
                .claim("id", member.getId()) // 사용자가 넣고 싶은 data : claim > payload에 저장, 탈취 생각해서 개인정보 넣으면 안됨
                .claim("role", member.getRole().name()) // enum 숫자로 관리
                .claim("userName", member.getUserName())
                .signWith(getSecretKey(), SignatureAlgorithm.HS512) // 서명
                .compact();
    }

    // 토큰 추출 : 아직 spring security랑 아무 관련 없음
    public Member getTokenDetails(String token) {
        Claims claims = getClaims(token);

        return Member.builder()
                .id(claims.get("id", Long.class))
                .userId(claims.getSubject())
                .userName(claims.get("userName", String.class))
                .role(
                        Role.valueOf(claims.get("role", String.class)) // String type > Role Type 파싱
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
        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());// yml에서 가져온 secretKey를 byte 단위로 쪼개기
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // spring security와 JWT 연결점 : 시큐리티 입장에서 로그인(인증)하려면 인증 객체가 필요함
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        // 권한 빼오기, 상황에 따라서 권한이 여러 개 들어올 수 있다
        // Claims에서 역할을 추출하고, GrantedAuthority로 변환
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(claims.get("role", String.class))
        );
        
        // UserDetails 객체 생성
        UserDetails userDetails = new User(claims.getSubject(), "", authorities);
        
        // UsernamePasswordAuthenticationToken 생성 : 시큐리티가 이해할 수 있는 토큰 > authentication 객체(인증 객체)를 만들었다
        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    // JWT 유효성 검사
    public int validToken(String token) {
        try {
            getClaims(token);

            return 1;
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            log.info("Token이 만료되었습니다.");
            return 2;
        } catch (Exception e) {
            // 복호화 과정에서 에러가 나면 유효하지 않은 토큰 > 탈취하려고 하거나, 다른 토큰을 가지고 왔을 때
            System.out.println("Token 복호화 에러 : " + e.getMessage());
            return 3;
        }
    }

}
