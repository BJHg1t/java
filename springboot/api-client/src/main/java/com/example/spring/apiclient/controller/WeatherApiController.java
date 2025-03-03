package com.example.spring.apiclient.controller;

import com.example.spring.apiclient.dto.WeatherRequestDTO;
import com.example.spring.apiclient.dto.WeatherResponseDTO;
import com.example.spring.apiclient.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherApiController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public WeatherResponseDTO getData(
            @RequestParam String serviceKey,
            @RequestParam int pageNo,
            @RequestParam int numOfRows,
            @RequestParam String  dataType,
            @RequestParam String  base_date,
            @RequestParam String  base_time,
            @RequestParam int nx,
            @RequestParam int ny
    ) {
        WeatherRequestDTO weatherRequestDTO = WeatherRequestDTO.builder()
                .serviceKey(serviceKey)
                .pageNo(pageNo)
                .numOfRows(numOfRows)
                .dataType(dataType)
                .base_date(base_date)
                .base_time(base_time)
                .nx(nx)
                .ny(ny)
                .build();
        return weatherService.getWeather(weatherRequestDTO);
    }
}

// http://localhost:1234/weather?serviceKey=opV76JHAiKh6IJH3slJZ%2FehqPP8EBGXFGc7B1JXb%2BOMfnx%2BmYKljlDXhMoJNwrxX4bvyUjglvUpUYl7TmgRFWA%3D%3D&pageNo=1&numOfRows=10&dataType=JSON&base_date=20250303&base_time=0630&nx=55&ny=127
