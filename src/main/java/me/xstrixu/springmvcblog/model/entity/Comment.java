package me.xstrixu.springmvcblog.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @OneToOne
    private User user;
}
