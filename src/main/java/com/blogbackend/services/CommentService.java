package com.blogbackend.services;

import com.blogbackend.error.NotFoundException;
import com.blogbackend.models.Comment;
import com.blogbackend.models.Post;
import com.blogbackend.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    public Comment save(Comment comment, Long post_id) {
        if (comment.getCommentId() == null) {
            Post existingPost = postService.getById(post_id).orElseThrow(
                    () -> new NotFoundException("No post with id " + post_id)
            );
            comment.setPost(existingPost);
            comment.setCreatedAt(LocalDateTime.now());
        }
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getPostComments(Long post_id) {
        Post existingPost = postService.getById(post_id).orElseThrow(
                () -> new NotFoundException("No post with id " + post_id)
        );
        return commentRepository.findCommentsByPostOrderByCreatedAtAsc(existingPost);
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