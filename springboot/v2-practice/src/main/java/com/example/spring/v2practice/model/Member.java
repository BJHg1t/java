package com.example.spring.v2practice.model;

import com.example.spring.v2practice.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private Long id;
    private String userId;
    private String password;
    private String userName;
    private Role role;
}
