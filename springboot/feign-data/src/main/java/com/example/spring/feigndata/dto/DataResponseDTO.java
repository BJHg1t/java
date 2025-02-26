package com.example.spring.feigndata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
//@NoArgsConstructor 기본 생성자를 만드는 것
@AllArgsConstructor //모든 arg를 가진 생성자를 만드는 것
public class DataResponseDTO {
    private Long id;
    private String name;
    private int value;
}
