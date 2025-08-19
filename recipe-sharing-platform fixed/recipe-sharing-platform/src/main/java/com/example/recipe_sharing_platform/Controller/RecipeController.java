package com.example.recipe_sharing_platform.Controller;

import com.example.recipe_sharing_platform.Entity.Recipe;
import com.example.recipe_sharing_platform.Request.RecipeRequest;
import com.example.recipe_sharing_platform.Response.Basic;
import com.example.recipe_sharing_platform.Response.ResponseFactory;
import com.example.recipe_sharing_platform.Service.RecipeService;
import com.example.recipe_sharing_platform.Utils.Translator;
import com.example.recipe_sharing_platform.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RecipeController {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private RecipeService recipeService;

    @PostMapping(value = "/create-recipe")
    ResponseEntity<Basic> createRecipe(@RequestBody RecipeRequest recipeRequest) {
        try {
            // Implement logic to create a new recipe
            Recipe recipe = recipeService.createRecipe(recipeRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, recipe, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("create recipe error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "/get-recipe")
    ResponseEntity<Basic> getRecipe() {
        try {
            // Implement logic to get a recipe
            List<Recipe> recipeList = recipeService.getRecipe();
            return responseFactory.buildSuccess(HttpStatus.OK, recipeList, ErrorCode.GET_INFO_SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
        } catch (Exception e) {
            log.error("get recipe error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "/get-recipe/{id}")
    ResponseEntity<Basic> getRecipeById(@PathVariable("id") Long id) {
        try {
            // Implement logic to get a recipe by ID
            Recipe recipe = recipeService.getRecipeById(id);
            return responseFactory.buildSuccess(HttpStatus.OK, recipe, ErrorCode.GET_INFO_SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
        } catch (Exception e) {
            log.error("get recipe by id error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "update-recipe/{id}")
    ResponseEntity<Basic> updateRecipe(@PathVariable("id") Long id, @RequestBody RecipeRequest recipeRequest) {
        try {
            // Implement logic to update a recipe
            Recipe recipe = recipeService.updateRecipe(id, recipeRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, recipe, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("update recipe error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "delete-recipe/{id}")
    ResponseEntity<Basic> deleteRecipe(@PathVariable("id") Long id) {
        try {
            // Implement logic to delete a recipe
            recipeService.deleteRecipe(id);
            return responseFactory.buildSuccess(HttpStatus.OK, "Deleted!", ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("delete recipe error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }
}
