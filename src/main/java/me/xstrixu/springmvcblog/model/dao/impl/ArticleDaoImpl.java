package me.xstrixu.springmvcblog.model.dao.impl;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.model.dao.ArticleDao;
import me.xstrixu.springmvcblog.model.entity.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleDaoImpl implements ArticleDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(Article article) {
        Session session = sessionFactory.getCurrentSession();

        session.save(article);
    }
}
