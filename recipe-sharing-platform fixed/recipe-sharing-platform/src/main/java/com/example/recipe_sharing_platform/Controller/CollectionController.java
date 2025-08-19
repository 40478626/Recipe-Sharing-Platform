package com.example.recipe_sharing_platform.Controller;

import com.example.recipe_sharing_platform.Entity.RecipeCollection;
import com.example.recipe_sharing_platform.Request.CollectionRequest;
import com.example.recipe_sharing_platform.Response.Basic;
import com.example.recipe_sharing_platform.Response.ResponseFactory;
import com.example.recipe_sharing_platform.Service.RecipeCollectionService;
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
public class CollectionController {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private RecipeCollectionService recipeCollectionService;

    @PostMapping(value = "create-collection")
    ResponseEntity<Basic> createCollection(@RequestBody CollectionRequest collectionRequest) {
        try {
            // Implement logic to create a new dietary preference
            RecipeCollection recipeCollection = recipeCollectionService.createCollection(collectionRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, recipeCollection, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("Error creating dietary preference", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "get-collection/{id}")
    ResponseEntity<Basic> getCollectionById(@PathVariable Long id) {
        try {
            // Implement logic to get a dietary preference by id
            RecipeCollection recipeCollection = recipeCollectionService.getCollectionById(id);
            return responseFactory.buildSuccess(HttpStatus.OK, recipeCollection, ErrorCode.GET_INFO_SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
        } catch (Exception e) {
            log.error("get dietary preference error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "get-collection")
    ResponseEntity<Basic> getCollection() {
        try {
            // Implement logic to get a dietary preference
            List<RecipeCollection> recipeCollectionList = recipeCollectionService.getCollection();
            return responseFactory.buildSuccess(HttpStatus.OK, recipeCollectionList, ErrorCode.GET_INFO_SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
        } catch (Exception e) {
            log.error("get recipe error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "update-collection/{id}")
    ResponseEntity<Basic> updateCollection(@PathVariable Long id, @RequestBody CollectionRequest collectionRequest) {
        try {
            // Implement logic to update a dietary preference
            RecipeCollection recipeCollection = recipeCollectionService.updateCollection(id, collectionRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, recipeCollection, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("update dietary preference error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "delete-collection/{id}")
    ResponseEntity<Basic> deleteCollection(@PathVariable Long id) {
        try {
            // Implement logic to delete a dietary preference
            recipeCollectionService.deleteCollection(id);
            return responseFactory.buildSuccess(HttpStatus.OK, "Deleted!", ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("delete dietary preference error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }
}
