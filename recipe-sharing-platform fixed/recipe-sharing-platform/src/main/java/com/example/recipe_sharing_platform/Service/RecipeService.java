package com.example.recipe_sharing_platform.Service;

import com.example.recipe_sharing_platform.Entity.Recipe;
import com.example.recipe_sharing_platform.Request.RecipeRequest;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe(RecipeRequest recipeRequest);
    List<Recipe> getRecipe();
    Recipe getRecipeById(Long id);
    Recipe updateRecipe(Long id, RecipeRequest recipeRequest);
    void deleteRecipe(Long id);
}
