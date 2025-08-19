package com.example.recipe_sharing_platform.Service;

import com.example.recipe_sharing_platform.Entity.Comment;
import com.example.recipe_sharing_platform.Request.CommentRequest;

import java.util.List;

public interface CommentService {
    // Implement method to create a new comment
    Comment createComment(CommentRequest commentRequest);
    List<Comment> getCommentByRecipeId(Long id);
    void deleteComment(Long id);
    Comment updateComment(Long id, CommentRequest commentRequest);
}
