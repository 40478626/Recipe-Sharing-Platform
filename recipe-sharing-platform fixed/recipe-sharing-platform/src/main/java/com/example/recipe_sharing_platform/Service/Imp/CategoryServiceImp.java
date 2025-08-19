package com.example.recipe_sharing_platform.Service.Imp;

import com.example.recipe_sharing_platform.Entity.Category;
import com.example.recipe_sharing_platform.Repository.CategoryRepo;
import com.example.recipe_sharing_platform.Request.CategoryRequest;
import com.example.recipe_sharing_platform.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Category createCategory(CategoryRequest categoryRequest) {
        // Implement logic to create a new category
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);  // Save the category to the database
        return category;
    }

    @Override
    public List<Category> getCategory() {
        // Implement logic to retrieve a category
        List<Category> categories = categoryRepo.findAll();
        return categories;
    }

    @Override
    public Category updateCategory(Long id, CategoryRequest categoryRequest) {
        // Implement logic to update a category
        Category category = categoryRepo.findById(id).orElse(null);
        if (category!= null) {
            category.setName(categoryRequest.getName());
            categoryRepo.save(category);  // Save the updated category to the database
            return category;
        } else {
            throw new IllegalStateException("Category Not Found");
        }
    }

    @Override
    public void deleteCategory(Long id) {
        // Implement logic to delete the category
        Category category = categoryRepo.findById(id).orElse(null);
        if (category!= null) {
            categoryRepo.deleteById(id);  // Delete the category from the database
        } else {
            throw new IllegalStateException("Category Not Found");
        }
    }
}
