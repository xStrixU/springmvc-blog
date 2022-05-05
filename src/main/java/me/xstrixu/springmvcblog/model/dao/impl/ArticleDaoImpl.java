package me.xstrixu.springmvcblog.model.dao.impl;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.model.dao.ArticleDao;
import me.xstrixu.springmvcblog.model.entity.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleDaoImpl implements ArticleDao {

    private final SessionFactory sessionFactory;

    @Override
    public void save(Article article) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(article);
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        var article = new Article();
        article.setId(id);

        session.delete(article);
    }

    @Override
    public List<Article> getAllArticles() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("FROM Article", Article.class).list();
    }

    @Override
    public Optional<Article> getArticle(Long id) {
        Session session = sessionFactory.getCurrentSession();

        return Optional.ofNullable(session.get(Article.class, id));
    }


}
