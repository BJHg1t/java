package com.example.spring.apiclient.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherRequestDTO {
    private String serviceKey;
    private int pageNo;
    private int numOfRows;
    private String dataType;
    private String base_date;
    private String base_time;
    private int nx;
    private int ny;
}
