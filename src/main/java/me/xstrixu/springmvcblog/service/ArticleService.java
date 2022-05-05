package me.xstrixu.springmvcblog.service;

import me.xstrixu.springmvcblog.model.dto.ArticleDto;
import me.xstrixu.springmvcblog.model.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    void save(ArticleDto articleDto);

    void updateArticle(Long id, ArticleDto articleDto);

    void deleteArticle(Long id);

    void addComment(Long id, String content);

    void deleteComment(Long articleId, Long commentId);

    List<Article> getAllArticles();

    Optional<Article> getArticle(Long id);
}
