package me.xstrixu.springmvcblog.controller;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.model.dto.ArticleDto;
import me.xstrixu.springmvcblog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/add-article")
    public String getAddArticleView(Model model) {
        model.addAttribute("article", new ArticleDto());

        return "add-article";
    }

    @PostMapping("/add-article")
    public String addArticle(
            @Valid @ModelAttribute("article") ArticleDto articleDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "add-article";
        }

        articleService.save(articleDto);

        return "redirect:/";
    }
}
