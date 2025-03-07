package com.example.spring.v2practice.controller;

import com.example.spring.v2practice.dto.BoardListResponseDTO;
import com.example.spring.v2practice.model.Article;
import com.example.spring.v2practice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    public void saveArticle(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("hiddenUserId") String userId,
            @RequestParam("file") MultipartFile file

    ) {
        boardService.saveArticle(userId, title, content, file);
    }

    @GetMapping
    public BoardListResponseDTO getBoards(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        List<Article> articles = boardService.getBoardArticles(page, size);
        int totalArticleCnt = boardService.getTotalArticleCnt();
        boolean last = (page * size) >= totalArticleCnt;

        return BoardListResponseDTO.builder()
                .articles(articles)
                .last(last)
                .build();
    }
}
