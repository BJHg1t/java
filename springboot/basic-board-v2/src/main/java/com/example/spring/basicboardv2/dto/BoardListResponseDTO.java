package com.example.spring.basicboardv2.dto;

import com.example.spring.basicboardv2.model.Article;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardListResponseDTO {
    List<Article> articles; // model은 DB 자체 스펙이기 때문에 model을 response로 프론트에 노출되면 안됨, 귀찮아서 썻을 뿐
    boolean last;
}
