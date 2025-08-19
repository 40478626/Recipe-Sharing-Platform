package com.example.recipe_sharing_platform.Repository;

import com.example.recipe_sharing_platform.Entity.Rate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepo extends JpaRepository<Rate, Long> {
    // Rate findByRecipeId(Long id);
    Rate findByUserId(String id);
    List<Rate> findAllByRecipeId(Long recipeId);
}
