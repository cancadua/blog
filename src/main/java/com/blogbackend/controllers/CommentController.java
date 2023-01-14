package com.blogbackend.controllers;

import com.blogbackend.models.Comment;
import com.blogbackend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/public/posts/{post_id}/comments")
    public ResponseEntity<?> getPostComments(@PathVariable Long post_id) {
        return new ResponseEntity<>(commentService.getPostComments(post_id), HttpStatus.OK);
    }

    @PostMapping("/posts/{post_id}/comments")
    public ResponseEntity<?> createNewComment(@PathVariable Long post_id, @RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.save(comment, post_id), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{post_id}/comments/{comment_id}")
    public ResponseEntity<?> updateComment(@PathVariable Long comment_id, @RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.edit(comment, comment_id), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{post_id}/comments/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long comment_id) {
        return new ResponseEntity<>(commentService.delete(comment_id), HttpStatus.OK);
    }
}
