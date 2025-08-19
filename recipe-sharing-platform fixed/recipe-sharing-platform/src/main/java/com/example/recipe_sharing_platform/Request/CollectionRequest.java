package com.example.recipe_sharing_platform.Request;

import lombok.Data;

import java.util.List;

@Data
public class CollectionRequest {
    private String name;
    private List<String> recipeIds;
}
