package com.example.spring.v1practice.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private String userId;
    private String password;
    private String userName;
}
