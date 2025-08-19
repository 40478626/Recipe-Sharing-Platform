package com.example.recipe_sharing_platform.Service;

import com.example.recipe_sharing_platform.Entity.Category;
import com.example.recipe_sharing_platform.Request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequest categoryRequest);
    List<Category> getCategory();
    Category updateCategory(Long id, CategoryRequest categoryRequest);
    void deleteCategory(Long id);
}
