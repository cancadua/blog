package com.blogbackend.services;

import com.blogbackend.error.NotFoundException;
import com.blogbackend.error.UnauthorizedException;
import com.blogbackend.models.Comment;
import com.blogbackend.models.Post;
import com.blogbackend.models.User;
import com.blogbackend.models.UserDetailsImpl;
import com.blogbackend.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (comment.getCommentId() == null) {
            Post existingPost = postService.getById(post_id).orElseThrow(
                    () -> new NotFoundException("No post with id " + post_id)
            );
            comment.setPost(existingPost);
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUser(new User(currentUser.getUserId()));
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

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        System.out.println(SecurityContextHolder
                .getContext());

        if (auth.getName().equals(existingComment.getUser().getUsername()) ||
                auth.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority()
                                .equals("ROLE_ADMIN"))) {
            existingComment.setContent(comment.getContent());
            return save(existingComment, existingComment.getPost().getPostId());
        } else throw new UnauthorizedException("Can't edit posts of user " + existingComment.getUser().getUsername());
    }

    public boolean delete(Long comment_id) {
        Comment existingComment = commentRepository.findById(comment_id).orElseThrow(
            () -> new NotFoundException("No comment with id " + comment_id)
    );
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth.getName().equals(existingComment.getUser().getUsername()) ||
                auth.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority()
                                .equals("ROLE_ADMIN"))) {
            return commentRepository.deleteCommentByCommentId(comment_id) > 0;
        } else throw new UnauthorizedException("Can't delete posts of user " + existingComment.getUser().getUsername());
    }
}