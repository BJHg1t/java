package com.example.spring.v2practice.mapper;

import com.example.spring.v2practice.model.Article;
import com.example.spring.v2practice.model.Paging;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void saveArticle(Article article);
    List<Article> getArticles(Paging page);
    int getArticleCnt();
}
