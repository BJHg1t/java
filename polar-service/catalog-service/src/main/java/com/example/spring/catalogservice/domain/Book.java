package com.example.spring.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;

@Builder
public record Book(
        @Id
        Long id,
        @NotBlank(message = "The book ISBN must be defined.")
        @Pattern(
                regexp = "^([0-9]{10}|[0-9]{13})$",
                message = "The ISBN format must be valid."
        )
        String isbn,
        @NotBlank(message = "The book author must be defined.")
        String author,
        @NotBlank(message = "The book title must be defined.")
        String title,
        @NotNull(message = "The book price must be defined.")
        @Positive(message = "The book price must be greater than zero")
        Double price,
        @Column("create_at")
        @CreatedDate // 생성 시간을 넣어 줌
        Instant createAt,
        @Column("last_modified_at")
        @LastModifiedDate // 수정 시간을 넣어 줌
        Instant lastModifiedAt,
        @Version // data 들어가거나 변경될 때 동시성 문제 해결
        int version // 낙관적 락 참고
) {}
