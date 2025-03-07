package com.example.spring.v2practice.dto;

import com.example.spring.v2practice.model.Article;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardListResponseDTO {
    List<Article> articles;
    boolean last;
}
