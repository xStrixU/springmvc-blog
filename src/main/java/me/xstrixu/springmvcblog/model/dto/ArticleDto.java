package me.xstrixu.springmvcblog.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleDto {

    @NotBlank(message = "Title is required!")
    private String title;

    @NotBlank(message = "Description is required!")
    private String description;

    @NotBlank(message = "ImageURL is required!")
    private String imageURL;

    @NotBlank(message = "Content is required!")
    private String content;
}
