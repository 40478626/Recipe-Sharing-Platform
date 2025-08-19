package com.example.recipe_sharing_platform.Service.Imp;

import com.example.recipe_sharing_platform.Entity.RecipeCollection;
import com.example.recipe_sharing_platform.Repository.RecipeCollectionRepo;
import com.example.recipe_sharing_platform.Request.CollectionRequest;
import com.example.recipe_sharing_platform.Service.RecipeCollectionService;
import com.example.recipe_sharing_platform.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeCollectionServiceImp implements RecipeCollectionService {

    @Autowired
    private RecipeCollectionRepo recipeCollectionRepo;

    @Autowired
    private SecurityConfig securityConfig;

    @Override
    public RecipeCollection createCollection(CollectionRequest collectionRequest) {
        RecipeCollection recipeCollection = new RecipeCollection();
        recipeCollection.setName(collectionRequest.getName());
        recipeCollection.setUserId(securityConfig.getUserId());
        String joinedIds = collectionRequest.getRecipeIds()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        recipeCollection.setRecipeId(joinedIds);
        recipeCollectionRepo.save(recipeCollection);
        return recipeCollection;
    }

    @Override
    public List<RecipeCollection> getCollection() {
        List<RecipeCollection> recipeCollectionList = recipeCollectionRepo.findAll();
        return recipeCollectionList;
    }

    @Override
    public RecipeCollection updateCollection(Long id, CollectionRequest collectionRequest) {
        RecipeCollection recipeCollection = recipeCollectionRepo.findById(id).orElse(null);
        if (recipeCollection!= null) {
            recipeCollection.setName(collectionRequest.getName());
            recipeCollection.setUserId(securityConfig.getUserId());
            String joinedIds = collectionRequest.getRecipeIds()
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        recipeCollection.setRecipeId(joinedIds);    

            recipeCollectionRepo.save(recipeCollection);
            return recipeCollection;
        } else {
            throw new IllegalStateException("Recipe Collection Not Found");
        }
    }

    @Override
    public void deleteCollection(Long id) {
        RecipeCollection recipeCollection = recipeCollectionRepo.findById(id).orElse(null);
        if (recipeCollection!= null) {
            recipeCollectionRepo.deleteById(id);
        } else {
            throw new IllegalStateException("Recipe Collection Not Found");
        }
    }

    @Override
    public RecipeCollection getCollectionById(Long id) {
        RecipeCollection recipeCollection = recipeCollectionRepo.findById(id).orElse(null);
        if (recipeCollection!= null) {
            return recipeCollection;
        } else {
            throw new IllegalStateException("Recipe Collection Not Found");
        }
    }
}
