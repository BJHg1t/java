package com.example.spring.webfrontservice.dto;

import lombok.Getter;

@Getter
public class CreateOrderRequestDTO {
    private String isbn;
    private Integer quantity;
}
