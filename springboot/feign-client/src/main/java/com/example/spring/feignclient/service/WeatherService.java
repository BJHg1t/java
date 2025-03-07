package com.example.spring.feignclient.service;

import com.example.spring.feignclient.client.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;
    @Value("${weather.api.key}") // yml에 접근하는 방법
    private String serviceKey;

    pu
}
