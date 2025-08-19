package com.example.recipe_sharing_platform.Entity;

import com.example.recipe_sharing_platform.Utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="RECIPE")
public class Recipe {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "INGREDIENTS", length = 1000)
    private String ingredients;

    @Column(name = "COOKING_TIME")
    private Long cookingTime;

    @Column(name = "DIETARY_PREFERENCES")
    private String dietaryPreferences;

    @Column(name = "IMAGE_ID")
    private String imageId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CREATED_AT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp createdAt = TimeUtils.getCurrentTimestamp();

    @Column(name = "UPDATED_AT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp updatedAt = TimeUtils.getCurrentTimestamp();
}

