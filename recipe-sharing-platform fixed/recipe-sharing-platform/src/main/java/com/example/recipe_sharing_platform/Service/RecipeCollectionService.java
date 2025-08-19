package com.example.recipe_sharing_platform.Service;

import com.example.recipe_sharing_platform.Entity.RecipeCollection;
import com.example.recipe_sharing_platform.Request.CollectionRequest;

import java.util.List;

public interface RecipeCollectionService {
    RecipeCollection createCollection(CollectionRequest collectionRequest);
    RecipeCollection getCollectionById(Long id);
    List<RecipeCollection> getCollection();
    RecipeCollection updateCollection(Long id, CollectionRequest collectionRequest);
    void deleteCollection(Long id);
}
