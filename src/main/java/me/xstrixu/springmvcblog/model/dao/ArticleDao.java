package me.xstrixu.springmvcblog.model.dao;

import me.xstrixu.springmvcblog.model.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {

    void save(Article article);

    void delete(Long id);

    List<Article> getAllArticles();

    Optional<Article> getArticle(Long id);
}
