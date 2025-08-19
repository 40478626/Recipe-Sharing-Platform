package com.example.recipe_sharing_platform.Request;

import lombok.Data;

@Data
public class CommentRequest {
    private Long recipeId;
    private String comment;
}
