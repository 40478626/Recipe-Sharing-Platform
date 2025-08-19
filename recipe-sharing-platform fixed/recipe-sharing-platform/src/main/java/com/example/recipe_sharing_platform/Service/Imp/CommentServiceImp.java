package com.example.recipe_sharing_platform.Service.Imp;

import com.example.recipe_sharing_platform.Entity.Comment;
import com.example.recipe_sharing_platform.Repository.CommentRepo;
import com.example.recipe_sharing_platform.Request.CommentRequest;
import com.example.recipe_sharing_platform.Service.CommentService;
import com.example.recipe_sharing_platform.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private SecurityConfig securityConfig;

    // Implement method to create a new comment
    public Comment createComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setRecipeId(commentRequest.getRecipeId());
        comment.setUserId(securityConfig.getUserId());
        comment.setComment(commentRequest.getComment());
        commentRepo.save(comment);
        return comment;
    }

    // Implement method to get all comments by recipe id
    public List<Comment> getCommentByRecipeId(Long id) {
        List<Comment> commentList = commentRepo.findAllByRecipeId(id);
        return commentList;
    }

    // Implement method to delete a comment
    public void deleteComment(Long id) {
        Comment comment = commentRepo.findById(id).orElse(null);
        if (comment != null) {
            commentRepo.deleteById(id);
        } else {
            throw new IllegalStateException("Comment Not Found");
        }
    }

    // Implement method to update a comment
    public Comment updateComment(Long id, CommentRequest commentRequest) {
        Comment comment = commentRepo.findById(id).orElse(null);
        if (comment != null) {
            comment.setComment(commentRequest.getComment());
            commentRepo.save(comment);
            return comment;
        } else {
            throw new IllegalStateException("Comment Not Found");
        }
    }
}
