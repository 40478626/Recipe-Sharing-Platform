package com.example.recipe_sharing_platform.Service.Imp;

import com.example.recipe_sharing_platform.Entity.Recipe;
import com.example.recipe_sharing_platform.Repository.RecipeRepo;
import com.example.recipe_sharing_platform.Request.RecipeRequest;
import com.example.recipe_sharing_platform.Service.RecipeService;
import com.example.recipe_sharing_platform.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImp implements RecipeService {

    @Autowired
    private RecipeRepo recipeRepo;

    @Autowired
    private SecurityConfig securityConfig;

    @Override
    public Recipe createRecipe(RecipeRequest recipeRequest) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeRequest.getName());
        recipe.setCategoryName(recipeRequest.getCategory());
        recipe.setImageId(recipeRequest.getImageId());
        recipe.setIngredients(recipeRequest.getIngredients());
        recipe.setCookingTime(recipeRequest.getCookingTime());
        recipe.setDietaryPreferences(recipeRequest.getDietaryPreferences());
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setUserId(securityConfig.getUserId());
        recipeRepo.save(recipe);  // Save the recipe to the database
        return recipe;
    }

    @Override
    public List<Recipe> getRecipe() {
        // Implement logic to retrieve all recipes
        List<Recipe> recipes = recipeRepo.findAll();
        return recipes;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        // Implement logic to retrieve a recipe by ID
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if (recipe != null) {
            return recipe;
        } else {
            throw new IllegalStateException("Recipe Not Found!");
        }
    }

    @Override
    public Recipe updateRecipe(Long id, RecipeRequest recipeRequest) {
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if (recipe!= null) {
            recipe.setName(recipeRequest.getName());
            recipe.setCategoryName(recipeRequest.getCategory());
            recipe.setImageId(recipeRequest.getImageId());
            recipe.setIngredients(recipeRequest.getIngredients());
            recipe.setCookingTime(recipeRequest.getCookingTime());
            recipe.setDietaryPreferences(recipeRequest.getDietaryPreferences());
            recipe.setDescription(recipeRequest.getDescription());
            recipeRepo.save(recipe);  // Save the updated recipe to the database
            return recipe;
        } else {
            throw new IllegalStateException("Recipe Not Found!");
        }
    }

    @Override
    public void deleteRecipe(Long id) {
        // Implement logic to delete the recipe
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if (recipe!= null) {
            recipeRepo.deleteById(id); // Delete the recipe from the database
        }  else {
            throw new IllegalStateException("Recipe Not Found!");
        }
    }
}
