package me.xstrixu.springmvcblog.service.impl;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.exception.UserNotFoundException;
import me.xstrixu.springmvcblog.model.dao.UserDao;
import me.xstrixu.springmvcblog.model.dto.UserRegistrationDto;
import me.xstrixu.springmvcblog.model.entity.Role;
import me.xstrixu.springmvcblog.model.entity.User;
import me.xstrixu.springmvcblog.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Optional<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    @Transactional
    public void save(UserRegistrationDto userRegistrationDto) {
        var user = new User();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRoles(List.of(new Role("ROLE_USER")));

        userDao.save(user);
    }

    @Override
    @Transactional
    public User getCurrentUser() throws UserNotFoundException {
        var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDao.getByEmail(currentUserDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User with email " + currentUserDetails.getUsername() + " not found in database!"));
    }
}
