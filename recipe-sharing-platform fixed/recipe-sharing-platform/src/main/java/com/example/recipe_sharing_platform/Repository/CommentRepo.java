package com.example.recipe_sharing_platform.Repository;

import com.example.recipe_sharing_platform.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository <Comment, Long> {
    List<Comment> findAllByRecipeId(Long id);
}
