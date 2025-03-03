package com.example.spring.apiclient.service;

import com.example.spring.apiclient.client.WeatherClient;
import com.example.spring.apiclient.dto.WeatherRequestDTO;
import com.example.spring.apiclient.dto.WeatherResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherResponseDTO getWeather(WeatherRequestDTO weatherRequestDTO) {
        return weatherClient.getData(
                weatherRequestDTO.getServiceKey(),
                weatherRequestDTO.getPageNo(),
                weatherRequestDTO.getNumOfRows(),
                weatherRequestDTO.getDataType(),
                weatherRequestDTO.getBase_date(),
                weatherRequestDTO.getBase_time(),
                weatherRequestDTO.getNx(),
                weatherRequestDTO.getNy()
        );
    }
}
