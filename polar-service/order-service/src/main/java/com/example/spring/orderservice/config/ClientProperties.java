package com.example.spring.orderservice.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

// yml의 값을 java에서 사용하게 가져오는 역할
@ConfigurationProperties(prefix = "polar") // polar 아래있는 정보를 가져오게 함
public record ClientProperties(
    @NotNull
    URI catalogServiceUri
) {}
