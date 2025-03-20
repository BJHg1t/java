package com.example.spring.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
// POJO기반 : spring boot는 최소한의 것만 담고 있는데 필요한 것을 썻을 때 무엇을 썻는지 알려줘야 어떤 작동을 할 지 spring boot가 이해함, ClientProperties 확인
@ConfigurationPropertiesScan
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
