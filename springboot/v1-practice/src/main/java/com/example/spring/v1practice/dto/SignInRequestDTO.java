package com.example.spring.v1practice.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignInRequestDTO {
    private String userId;
    private String password;
}
