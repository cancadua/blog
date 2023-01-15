package com.blogbackend.controllers;

import com.blogbackend.models.Comment;
import com.blogbackend.models.Post;
import com.blogbackend.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "testUser")
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    void successfullyGetPostCommentsTest() throws Exception {
        Post post = new Post(0L, "title", "content");
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("test content"));
        comments.add(new Comment("test content2"));

        when(commentService.getPostComments(any(Long.class))).thenReturn(comments);

        mockMvc.perform(get("/api/public/posts/" + post.getPostId() + "/comments")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void successfullyCreateCommentTest() throws Exception {
        Comment comment = new Comment( "test content");
        Post post = new Post(0L, "title", "content");

        when(commentService.save(any(Comment.class), any(Long.class))).thenReturn(comment);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String commentJson = objectMapper.writeValueAsString(comment);

        ResultActions result = mockMvc.perform(post("/api/posts/" + post.getPostId() + "/comments")
                .contentType("application/json")
                .content(commentJson)
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("test content"));
    }

    @Test
    void successfullyUpdateCommentTest() throws Exception {
        Post post = new Post(0L, "title", "content");
        Comment comment = new Comment(0L, "test content");

        when(commentService.edit(any(Comment.class), any(Long.class))).thenReturn(comment);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String commentJson = objectMapper.writeValueAsString(comment);

        ResultActions result = mockMvc.perform(put("/api/posts/" + post.getPostId() + "/comments/" + comment.getCommentId())
                .contentType("application/json")
                .content(commentJson)
        );
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("test content"));
    }

    @Test
    void successfullyDeleteCommentTest() throws Exception {
        Post post = new Post(0L, "title", "content");
        Comment comment = new Comment(0L,"test content");

        when(commentService.delete(any(Long.class))).thenReturn(true);
        ResultActions result = mockMvc.perform(delete("/api/posts/" + post.getPostId() + "/comments/" + comment.getCommentId()));

        result.andExpect(status().isOk());
    }



}
