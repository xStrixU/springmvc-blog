package me.xstrixu.springmvcblog.controller;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String getIndexView(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());

        return "index";
    }
}
