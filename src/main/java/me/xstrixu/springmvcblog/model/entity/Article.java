package me.xstrixu.springmvcblog.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "article")
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "image_url")
    private String imageURL;

    private String content;
}
