package me.xstrixu.springmvcblog.service.impl;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.exception.ArticleNotFoundException;
import me.xstrixu.springmvcblog.model.dao.ArticleDao;
import me.xstrixu.springmvcblog.model.dto.ArticleDto;
import me.xstrixu.springmvcblog.model.entity.Article;
import me.xstrixu.springmvcblog.model.entity.Comment;
import me.xstrixu.springmvcblog.model.entity.User;
import me.xstrixu.springmvcblog.service.ArticleService;
import me.xstrixu.springmvcblog.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final UserService userService;
    private final ArticleDao articleDao;

    @Override
    @Transactional
    public void save(ArticleDto articleDto) {
        User currentUser = userService.getCurrentUser();

        var article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setImageURL(articleDto.getImageURL());
        article.setContent(articleDto.getContent());
        article.setUser(currentUser);
        article.setComments(Collections.emptyList());

        articleDao.save(article);
    }

    @Override
    @Transactional
    public void updateArticle(Long id, ArticleDto articleDto) {
        Article article = articleDao.getArticle(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article with id " + id + " not found!"));
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setImageURL(articleDto.getImageURL());
        article.setContent(articleDto.getContent());

        articleDao.save(article);
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        articleDao.delete(id);
    }

    @Override
    @Transactional
    public void addComment(Long id, String content) {
        Article article = articleDao.getArticle(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article with id " + id + " not found!"));
        User currentUser = userService.getCurrentUser();

        var comment = new Comment();
        comment.setUser(currentUser);
        comment.setContent(content);

        article.getComments().add(comment);
        articleDao.save(article);
    }

    @Override
    @Transactional
    public void deleteComment(Long articleId, Long commentId) {
        Article article = articleDao.getArticle(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article with id " + articleId + " not found!"));
        List<Comment> filteredComments = article.getComments().stream()
                .filter(comment -> !comment.getId().equals(commentId))
                .toList();

        article.setComments(filteredComments);
        articleDao.save(article);
    }

    @Override
    @Transactional
    public List<Article> getAllArticles() {
        return articleDao.getAllArticles();
    }

    @Override
    @Transactional
    public Optional<Article> getArticle(Long id) {
        return articleDao.getArticle(id);
    }
}
