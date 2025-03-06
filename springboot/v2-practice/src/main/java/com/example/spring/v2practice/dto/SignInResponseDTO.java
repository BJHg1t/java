package com.example.spring.v2practice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponseDTO {
    private boolean isLoggined;
    private String userId;
    private String username;
    private String token;
}
