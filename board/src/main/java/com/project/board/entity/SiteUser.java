package com.project.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class SiteUser {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "siteUser", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();
}
