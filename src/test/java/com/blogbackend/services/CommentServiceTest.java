package com.blogbackend.services;

import com.blogbackend.models.Comment;
import com.blogbackend.models.Post;
import com.blogbackend.models.User;
import com.blogbackend.models.UserDetailsImpl;
import com.blogbackend.repositories.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    CommentService commentService;

    @Mock
    PostService postService;

    @Mock
    CommentRepository commentRepository;


    @AfterEach
    void tearDown(){
        commentRepository.deleteAll();
    }

    @Test
    void getPostCommentsTest() {
        Post post = new Post(0L, "title", "content");
        Optional<Post> optionalPost = Optional.of(post);
        List<Comment> list = new ArrayList<>();
        Comment comment = new Comment("test content");
        Comment comment2 = new Comment("test content2");
        Comment comment3 = new Comment("test content3");
        list.add(comment);
        list.add(comment2);
        list.add(comment3);

        when(commentRepository.findCommentsByPostOrderByCreatedAtAsc(any(Post.class))).thenReturn(list);
        when(postService.getById(any(Long.class))).thenReturn(optionalPost);
        List<Comment> commentList = commentService.getPostComments(post.getPostId());

        assertEquals(3, commentList.size());
    }

    @Test
    void saveCommentTest() {
        UserDetailsImpl principal = new UserDetailsImpl("user");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);

        Post post = new Post(0L, "title", "content");
        Optional<Post> optionalPost = Optional.of(post);
        Comment comment = new Comment("content");

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(postService.getById(any(Long.class))).thenReturn(optionalPost);
        Comment createdComment = commentService.save(comment, post.getPostId());

        assertNotNull(createdComment.getPost());
        assertEquals(comment.getContent(), createdComment.getContent());
    }

    @Test
    void editCommentTest() {
        UserDetailsImpl principal = new UserDetailsImpl("user");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(authentication.getName()).thenReturn(principal.getUsername());
        SecurityContextHolder.setContext(securityContext);

        Post post = new Post(0L, "title", "content");
        Comment comment = new Comment(0L, "content", new User("user"));
        comment.setPost(post);
        Optional<Comment> optionalComment = Optional.of(comment);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentRepository.findById(any(Long.class))).thenReturn(optionalComment);
        Comment editedComment = commentService.edit(comment, comment.getCommentId());

        assertEquals(comment.getContent(), editedComment.getContent());
    }

    @Test
    void deleteCommentTest() {
        UserDetailsImpl principal = new UserDetailsImpl("user");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(principal.getUsername());
        SecurityContextHolder.setContext(securityContext);

        Comment comment = new Comment(0L, "content", new User("user"));
        Optional<Comment> optionalComment = Optional.of(comment);

        when(commentRepository.deleteCommentByCommentId(any(Long.class))).thenReturn(1);
        when(commentRepository.findById(any(Long.class))).thenReturn(optionalComment);
        boolean isDeleted = commentService.delete(comment.getCommentId());

        assertTrue(isDeleted);
    }
}
