package com.example.recipe_sharing_platform.Repository;

import com.example.recipe_sharing_platform.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository <Category, Long> {
}
