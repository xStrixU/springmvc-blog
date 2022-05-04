package me.xstrixu.springmvcblog.service.impl;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.model.dao.ArticleDao;
import me.xstrixu.springmvcblog.model.dto.ArticleDto;
import me.xstrixu.springmvcblog.model.entity.Article;
import me.xstrixu.springmvcblog.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;

    @Override
    @Transactional
    public void save(ArticleDto articleDto) {
        var article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setImageURL(articleDto.getImageURL());
        article.setContent(articleDto.getContent());

        articleDao.save(article);
    }
}
