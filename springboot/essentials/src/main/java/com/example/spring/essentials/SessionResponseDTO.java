package com.example.spring.essentials;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SessionResponseDTO {
    private String id;
    private String pw;
    private String username;
}
