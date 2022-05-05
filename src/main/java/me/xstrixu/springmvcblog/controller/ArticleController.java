package me.xstrixu.springmvcblog.controller;

import lombok.RequiredArgsConstructor;
import me.xstrixu.springmvcblog.model.dto.ArticleDto;
import me.xstrixu.springmvcblog.model.entity.Article;
import me.xstrixu.springmvcblog.model.entity.User;
import me.xstrixu.springmvcblog.service.ArticleService;
import me.xstrixu.springmvcblog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/add")
    public String getAddArticleView(Model model) {
        model.addAttribute("article", new ArticleDto());

        return "article/add-article";
    }

    @PostMapping("/add")
    public String addArticle(
            @Valid @ModelAttribute("article") ArticleDto articleDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "article/add-article";
        }

        articleService.save(articleDto);

        return "redirect:/";
    }

    @GetMapping("/edit")
    public String getArticlesToEditView(Model model) {
        User user = userService.getCurrentUser();

        model.addAttribute("articles", user.getArticles());

        return "article/articles-to-edit";
    }

    @GetMapping("/edit/{id}")
    public String getEditArticleView(@PathVariable Long id, Model model) {
        User user = userService.getCurrentUser();
        Optional<Article> editingArticle = user.getArticles().stream()
                .filter(article -> article.getId().equals(id))
                .findAny();

        if (editingArticle.isEmpty()) {
            return "redirect:/article/edit";
        }

        model.addAttribute("article", editingArticle.get());

        return "/article/edit-article";
    }

    @PostMapping("/edit")
    public String editArticle(
            @RequestParam Long id,
            @Valid @ModelAttribute("article") ArticleDto articleDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "article/edit-article";
        }

        articleService.updateArticle(id, articleDto);

        return "redirect:/article/edit";
    }

    @PostMapping("/delete")
    public String deleteArticle(@RequestParam Long id, @RequestParam(required = false) boolean admin) {
        System.out.println(id);
        articleService.deleteArticle(id);

        return "redirect:" + (admin ? "/" : "/article/edit");
    }

    @PostMapping("/add-comment")
    public String addComment(
            @RequestParam Long id,
            @RequestParam String content) {
        articleService.addComment(id, content);

        return "redirect:/";
    }

    @PostMapping("/delete-comment")
    public String deleteComment(
            @RequestParam Long articleId,
            @RequestParam Long commentId
    ) {
        articleService.deleteComment(articleId, commentId);

        return "redirect:/";
    }
}
