package com.example.spring.v1practice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponseDTO {
    private boolean success;
}
