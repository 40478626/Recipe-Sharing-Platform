package com.example.recipe_sharing_platform.Repository;

import com.example.recipe_sharing_platform.Entity.RecipeCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeCollectionRepo extends JpaRepository<RecipeCollection, Long> {
}
