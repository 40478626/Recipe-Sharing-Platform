package com.example.recipe_sharing_platform.Entity;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
@Table(name = "USER")
public class User {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}
