package com.example.spring.basicboardv2.controller;

import com.example.spring.basicboardv2.dto.BoardDeleteRequestDTO;
import com.example.spring.basicboardv2.dto.BoardDetailResponseDTO;
import com.example.spring.basicboardv2.dto.BoardListResponseDTO;
import com.example.spring.basicboardv2.model.Article;
import com.example.spring.basicboardv2.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        // String userId, String title, String content, MultipartFile file
        boardService.saveArticle(userId, title, content, file);
    }

    @GetMapping
    public BoardListResponseDTO getBoards(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        // 게시글 목록 가져오기
        List<Article> articles = boardService.getBoardArticles(page, size);
        // 전체 게시글 수 가져오기
        int totalArticleCnt = boardService.getTotalArticleCnt();
        // 마지막 페이지 여부 계산
        boolean last = (page * size) >= totalArticleCnt;

        return BoardListResponseDTO.builder()
                .articles(articles)
                .last(last) // true면 이동x
                .build();
    }

    @GetMapping("/{id}")
    public BoardDetailResponseDTO getBoardDetail(@PathVariable Long id) {
        return boardService.getBoardDetail(id)
                .toBoardDetailResponseDTO();
    }

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = boardService.downloadFile(fileName);

        // 한글 파일명을 URL 인코딩
        String encoded = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encoded)
                .body(resource);
    }

    @PutMapping
    public void updateArticle(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("hiddenUserId") String userId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("hiddenId") Long id,
            @RequestParam("hiddenFileFlag") boolean fileChanged,
            @RequestParam("hiddenFilePath") String filePath
    ) {
        System.out.println(title + " " + content + " " + userId + " " + id + " " + filePath);
        boardService.updateArticle(id, title, content, file, fileChanged, filePath);
    }

    @DeleteMapping("/{id}")
    /* map을 쓰면 어떤 값을 넣던 다 삭제됨, DTO를 사용하면 특정값이 아니면 실행이 안됨
    보통 json을 넘기는 방식을 사용하진 않음, 직접 DB로 가서 삭제하는 편 */
    public void deleteArticle(@PathVariable Long id, @RequestBody BoardDeleteRequestDTO requestDTO) {
        boardService.deleteBoardById(id, requestDTO);
    }

}
