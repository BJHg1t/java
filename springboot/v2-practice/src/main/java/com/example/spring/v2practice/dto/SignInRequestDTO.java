package com.example.spring.v2practice.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignInRequestDTO {
    private String username;
    private String password;
}
