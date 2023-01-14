package com.blogbackend.services;

import com.blogbackend.models.Comment;
import com.blogbackend.models.Post;
import com.blogbackend.repositories.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
    CommentRepository commentRepository;

    @AfterEach
    void tearDown(){
        commentRepository.deleteAll();
    }

    @Test
    void getPostCommentsTest() {
        Post post = new Post(0L, "title", "content");
        List<Comment> list = new ArrayList<>();
        Comment comment = new Comment("test content");
        Comment comment2 = new Comment("test content2");
        Comment comment3 = new Comment("test content3");
        list.add(comment);
        list.add(comment2);
        list.add(comment3);

        when(commentRepository.findCommentsByPostOrderByCreatedAtAsc(any(Long.class))).thenReturn(list);
        List<Comment> commentList = commentService.findAll(post.getPostId());

        assertEquals(3, commentList.size());
    }

    @Test
    void saveCommentTest() {
        Post post = new Post(0L, "title", "content");
        Comment comment = new Comment("content");

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        Comment createdComment = commentService.save(comment, post.getPostId());

        assertNotNull(createdComment.getPost());
        assertEquals(comment.getContent(), createdComment.getContent());
    }

    @Test
    void editCommentTest() {
        Post post = new Post(0L, "title", "content");
        Comment comment = new Comment(0L, "content");
        comment.setPost(post);
        Optional<Comment> optionalComment = Optional.of(comment);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentRepository.findById(any(Long.class))).thenReturn(optionalComment);
        Comment editedComment = commentService.edit(comment, comment.getCommentId());

        assertEquals(comment.getContent(), editedComment.getContent());
    }

    @Test
    void deleteCommentTest() {
        Comment comment = new Comment(0L, "content");

        when(commentRepository.deleteCommentByCommentId(any(Long.class))).thenReturn(true);
        boolean isDeleted = commentService.delete(comment.getCommentId());

        assertTrue(isDeleted);
    }
}
