package com.blogbackend.services;

import com.blogbackend.error.NotFoundException;
import com.blogbackend.models.Comment;
import com.blogbackend.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save(Comment comment, Long post_id) {
        return commentRepository.save(comment);
    }

    public List<Comment> findAll(Long post_id) {
        return commentRepository.findAll();
    }

    public Comment edit(Comment comment, Long comment_id) {
        Comment existingComment = commentRepository.findById(comment_id).orElseThrow(
                () -> new NotFoundException("No comment with id " + comment_id)
        );

        existingComment.setContent(comment.getContent());
        return save(existingComment, existingComment.getPost().getPostId());
    }

    public boolean delete(Long comment_id) {
        return commentRepository.deleteCommentByCommentId(comment_id);
    }
}
