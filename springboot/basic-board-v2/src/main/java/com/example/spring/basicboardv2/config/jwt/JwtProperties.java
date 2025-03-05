package com.example.spring.basicboardv2.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// yml 파일에 접근 : class로 관리 DTO와 비슷
@Getter
@Setter
@Component
@ConfigurationProperties("jwt") // 최상위 키워드를 입력, 용도에 맞게 한번에 관리할 수 있다, @Value 방식은 하나씩 가져다 쓸 수 있다
public class JwtProperties {
    private String issuer;
    private String secretKey;
}
