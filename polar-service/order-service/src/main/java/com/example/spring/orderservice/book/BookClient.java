package com.example.spring.orderservice.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class BookClient {

    private static final String BOOKS_ROOT_API = "/books/";
    private final WebClient webClient; // bean 등록을 해야 사용할 수 있음 = Spring Container는 webClient의 존재를 모르는 상태

}
