package com.example.spring.basicboardv2.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Paging {
    private int offset; // 시작값
    private int size;
}
