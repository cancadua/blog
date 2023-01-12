package com.blogbackend.services;

import com.blogbackend.models.Comment;
import com.blogbackend.repositories.CommentRepository;
import com.blogbackend.repositories.PostRepository;

import java.util.Collection;
import java.util.List;

public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(Comment newComment) {
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public void edit(Comment comment, Long comment_id) {
    }

    public void delete(Long commentId) {
    }
}
