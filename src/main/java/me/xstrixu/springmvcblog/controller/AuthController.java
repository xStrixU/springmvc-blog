package me.xstrixu.springmvcblog.controller;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.model.dto.UserRegistrationDto;
import me.xstrixu.springmvcblog.model.entity.User;
import me.xstrixu.springmvcblog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String getRegisterView(Model model) {
        model.addAttribute("user", new UserRegistrationDto());

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") UserRegistrationDto userDto,
            BindingResult bindingResult
    ) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            bindingResult.rejectValue("password", "error.registration", "Passwords does not match!");
            bindingResult.rejectValue("confirmPassword", "error.registration", "Passwords does not match!");
        }

        Optional<User> existingUser = userService.getByEmail(userDto.getEmail());

        if (existingUser.isPresent()) {
            bindingResult.rejectValue("email", "error.registration", "An account with this email already exists!");
        }

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        userService.save(userDto);

        return "index";
    }
}
