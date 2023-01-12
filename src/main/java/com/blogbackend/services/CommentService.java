package com.blogbackend.services;

import com.blogbackend.error.NotFoundException;
import com.blogbackend.models.Comment;
import com.blogbackend.models.Comment;
import com.blogbackend.repositories.CommentRepository;
import com.blogbackend.repositories.CommentRepository;

import java.util.Collection;
import java.util.List;

public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment edit(Comment comment, Long comment_id) {
        Comment existingComment = commentRepository.findById(comment_id).orElseThrow(
                () -> new NotFoundException("No comment with id " + comment_id)
        );

        existingComment.setContent(comment.getContent());
        return save(existingComment);
    }

    public boolean delete(Long comment_id) {
        commentRepository.deleteCommentByCommentId(comment_id);
        return true;
    }
}
