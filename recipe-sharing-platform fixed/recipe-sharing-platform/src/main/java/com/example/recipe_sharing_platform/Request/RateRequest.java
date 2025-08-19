package com.example.recipe_sharing_platform.Request;

import lombok.Data;

@Data
public class RateRequest {
    private Long recipeId;
    private int rate;
}
