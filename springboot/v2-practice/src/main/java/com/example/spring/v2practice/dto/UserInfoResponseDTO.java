package com.example.spring.v2practice.dto;

import com.example.spring.v2practice.type.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDTO {
    private Long id;
    private String userName;
    private String userId;
    private Role role;
}
