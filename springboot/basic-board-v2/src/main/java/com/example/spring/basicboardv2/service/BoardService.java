package com.example.spring.basicboardv2.service;

import com.example.spring.basicboardv2.mapper.BoardMapper;
import com.example.spring.basicboardv2.model.Article;
import com.example.spring.basicboardv2.model.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final FileService fileService;

    @Transactional // 일관성, 아예 되거나 아예 안되거나 (파일 생성, DB에 저장 중에 하나라도 안되면 전체적으로 롤백 ex)은행 업무)
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
        int offset = (page - 1) * size; // 페이지는 1부터 시작 offset 계산, 컴퓨터는 0시작
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

    public Article getBoardDetail(Long id) {
        return boardMapper.getArticleById(id);
    }

    public Resource downloadFile(String fileName) {
        return fileService.downloadFile(fileName);
    }
}
