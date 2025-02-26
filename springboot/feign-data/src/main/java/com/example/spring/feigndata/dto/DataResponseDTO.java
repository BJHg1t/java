package com.example.spring.feigndata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder // builder() 패턴을 구현하기 위한 어노테이션
// @NoArgsConstructor 기본 생성자를 만드는 어노테이션
@AllArgsConstructor // 모든 arg(인수)를 가진 생성자를 만드는 어노테이션
public class DataResponseDTO {
    private Long id;
    private String name;
    private int value;
}
