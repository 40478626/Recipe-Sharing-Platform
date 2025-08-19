package com.example.recipe_sharing_platform.Controller;

import com.example.recipe_sharing_platform.Entity.Category;
import com.example.recipe_sharing_platform.Request.CategoryRequest;
import com.example.recipe_sharing_platform.Response.Basic;
import com.example.recipe_sharing_platform.Response.ResponseFactory;
import com.example.recipe_sharing_platform.Service.CategoryService;
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
public class CategoryController {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/create-category")
    ResponseEntity<Basic> createCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            // Implement logic to create a new category
            Category category = categoryService.createCategory(categoryRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, category, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("create category error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "/get-category")
    ResponseEntity<Basic> getCategory() {
        try {
            // Implement logic to get a category
            List<Category> categories = categoryService.getCategory();
            return responseFactory.buildSuccess(HttpStatus.OK, categories, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
        } catch (Exception e) {
            log.error("get category error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "/update-category/{id}")
    ResponseEntity<Basic> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequest categoryRequest) {
        try {
            // Implement logic to update a category
            Category category = categoryService.updateCategory(id, categoryRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, category, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("update category error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "/delete-category/{id}")
    ResponseEntity<Basic> deleteCategory(@PathVariable("id") Long id) {
        try {
            // Implement logic to delete a category
            categoryService.deleteCategory(id);
            return responseFactory.buildSuccess(HttpStatus.OK, "Deleted!", ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("delete category error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }
}
