package com.example.recipe_sharing_platform.Controller;

import com.example.recipe_sharing_platform.Entity.Comment;
import com.example.recipe_sharing_platform.Request.CommentRequest;
import com.example.recipe_sharing_platform.Response.Basic;
import com.example.recipe_sharing_platform.Response.ResponseFactory;
import com.example.recipe_sharing_platform.Service.CommentService;
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
public class CommentController {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "create-comment")
    ResponseEntity<Basic> createComment(@RequestBody CommentRequest commentRequest) {
        try {
            // Implement logic to create a new comment
            Comment comment = commentService.createComment(commentRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, comment, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("Error creating new comment", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "get-comment/{id}")
    ResponseEntity<Basic> getComment(@PathVariable Long id) {
        try {
            // Implement logic to get a comment
            List<Comment> commentList = commentService.getCommentByRecipeId(id);
            return responseFactory.buildSuccess(HttpStatus.OK, commentList, ErrorCode.GET_INFO_SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
        } catch (Exception e) {
            log.error("Error getting comment", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "delete-comment/{id}")
    ResponseEntity<Basic> deleteComment(@PathVariable Long id) {
        try {
            // Implement logic to delete a comment
            commentService.deleteComment(id);
            return responseFactory.buildSuccess(HttpStatus.OK, null, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("Error deleting comment", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "update-comment/{id}")
    ResponseEntity<Basic> updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest) {
        try {
            // Implement logic to update a comment
            Comment comment = commentService.updateComment(id, commentRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, comment, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("Error updating comment", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }
}
