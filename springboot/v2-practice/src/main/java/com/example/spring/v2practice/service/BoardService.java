package com.example.spring.v2practice.service;

import com.example.spring.v2practice.mapper.BoardMapper;
import com.example.spring.v2practice.model.Article;
import com.example.spring.v2practice.model.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final FileService fileService;

    @Transactional
    public void saveArticle(String userId, String title, String content, MultipartFile file) {
        String path = null;
        if (!file.isEmpty()) {
            path = fileService.fileUpload(file);
        }

        boardMapper.saveArticle(
                Article.builder()
                        .title(title)
                        .content(content)
                        .userId(userId)
                        .filePath(path)
                        .build()
        );
    }

    public List<Article> getBoardArticles(int page, int size) {
        int offset = (page - 1) * size;
        return boardMapper.getArticles(
                Paging.builder()
                        .offset(offset)
                        .size(size)
                        .build()
        );
    }

    public int getTotalArticleCnt() {
        return boardMapper.getArticleCnt();
    }
}
