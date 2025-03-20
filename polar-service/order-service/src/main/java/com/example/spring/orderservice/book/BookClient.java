package com.example.spring.orderservice.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class BookClient {

    private static final String BOOKS_ROOT_API = "/books/";
    private final WebClient webClient; // bean 등록을 해야 사용할 수 있음 = Spring Container는 webClient의 존재를 모르는 상태

    public Mono<Book> getBookByIsbn(String isbn) {
        return webClient.get()
                .uri(BOOKS_ROOT_API + "/" + isbn)
                .retrieve() // 요청을 보내고 응답을 기다린다, 처리가 끝나면 알려줌
                .bodyToMono(Book.class)
                .timeout(Duration.ofSeconds(3)) // catalog-service가 죽어서 응답이 없을 때 끊기
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> Mono.empty())
                .retryWhen(
                        Retry.backoff(3, Duration.ofMillis(100)) // 0.001초 동안 3번 다시 시도해라
                )
                .onErrorResume(Exception.class, exception -> Mono.empty());
    }

}
