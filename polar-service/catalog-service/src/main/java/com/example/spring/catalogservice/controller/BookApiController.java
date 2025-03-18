package com.example.spring.catalogservice.controller;

import com.example.spring.catalogservice.domain.Book;
import com.example.spring.catalogservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookApiController {

    private final BookService bookService;

    @GetMapping
    public Iterable<Book> getBooks() {
        return bookService.viewBookList();
    }

    @PostMapping // 프로젝트 만들 때 domain 대신 DTO 만들어서 사용할 것
    public Book addBook(@RequestBody Book book) {
        return bookService.addBookToCatalog(book);
    }
}
