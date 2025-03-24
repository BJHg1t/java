package com.example.spring.orderservice.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookClient bookClient;

    public Flux<Book> getBooks() {
        return bookClient.getBooks()
                .defaultIfEmpty(buildMessageBook("Not Found!")); // 상수값만 반환
    }

    public Mono<Book> enrollBook(Book book) {
        return bookClient.getBookByIsbn(book.isbn())
                .flatMap(existingBook -> Mono.just(buildMessageBook("Already Exist!")))
                .switchIfEmpty(bookClient.enrollBook(book)); // 예외 상황에서 요청 process가 있을 때 사용해야 함
    }

    public Mono<Book> updateBook(String isbn, Book book) {
        return bookClient.getBookByIsbn(isbn)
                .flatMap(existingBook -> bookClient.updateBook(isbn, book))
                .defaultIfEmpty(buildMessageBook("Not Found!"));
    }

    public Mono<Void> deleteBook(String isbn) {
        return bookClient.deleteBook(isbn);
    }

    private static Book buildMessageBook(String message) {
        return Book.builder()
                .message(message)
                .build();
    }

}
