package com.example.spring.basicboardv2.config.security;

import com.example.spring.basicboardv2.model.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CustomUserDetails implements UserDetails {

    private Member member;
    private List<String> roles; // 어떤 역할이 부여된 것이 아닌 문자로써의 기능만 함


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream() // for문 처럼 list를 빨아들임
                .map(SimpleGrantedAuthority::new) // SimpleGrantedAuthority 타입으로 생성
                .collect(Collectors.toList()); // 결과물을 list로 만듬 > private List<SimpleGrantedAuthority> roles;
    } // 역할은 여러 개 부여될 수 있기 때문에

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
