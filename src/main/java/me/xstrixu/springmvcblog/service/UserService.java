package me.xstrixu.springmvcblog.service;

import me.xstrixu.springmvcblog.model.dto.UserRegistrationDto;
import me.xstrixu.springmvcblog.model.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getByEmail(String email);

    void save(UserRegistrationDto userRegistrationDto);
}
