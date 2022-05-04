package me.xstrixu.springmvcblog.model.dao.impl;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.model.dao.UserDao;
import me.xstrixu.springmvcblog.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Override
    public Optional<User> getByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);
        query.setFirstResult(0);
        query.setMaxResults(1);

        return query.uniqueResultOptional();
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();

        session.save(user);
    }
}
