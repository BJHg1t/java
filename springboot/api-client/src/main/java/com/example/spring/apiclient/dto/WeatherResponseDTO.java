package com.example.spring.apiclient.dto;

import lombok.Getter;

@Getter
public class WeatherResponseDTO {
    private int pageNo;
    private int numOfRows;
    private int totalCount;
    private String resultCode;
    private String resultMsg;
    private String  dataType;
    private String  base_date;
    private String  base_time;
    private int  nx;
    private int  ny;
    private String category;
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
}
