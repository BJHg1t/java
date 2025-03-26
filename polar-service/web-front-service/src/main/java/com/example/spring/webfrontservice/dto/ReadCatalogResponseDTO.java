package com.example.spring.webfrontservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadCatalogResponseDTO {
    private String isbn;
    private String title;
    private String author;
    private Double price;
}
