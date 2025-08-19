package com.example.recipe_sharing_platform.Request;

import lombok.Data;

@Data
public class RecipeRequest {
    private String name;
    private String ingredients;
    private String description;
    private Long cookingTime;
    private String dietaryPreferences;
    private String category;
    private String imageId;
    private String userId;
}
