package me.xstrixu.springmvcblog.model.dao;

import me.xstrixu.springmvcblog.model.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getByEmail(String email);

    void save(User user);
}
