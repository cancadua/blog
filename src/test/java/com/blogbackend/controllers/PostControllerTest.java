package com.blogbackend.controllers;

import com.blogbackend.models.Post;
import com.blogbackend.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    void successfullyGetAllPostsTest() throws Exception {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "test title", "test content"));
        posts.add(new Post(2L, "test title2", "test content2"));
        when(postService.findAll()).thenReturn(posts);

        mockMvc.perform(get("/api/public/posts")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void successfullyGetPostByIdTest() throws Exception {
        Post post = new Post(0L, "test title", "test content");
        when(postService.getPostById(0L)).thenReturn(post);

        mockMvc.perform(get("/api/public/posts/0")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test title"))
                .andExpect(jsonPath("$.content").value("test content"));
    }

    @Test
    void successfullyCreatePostTest() throws Exception {
        Post post = new Post("test title", "test content");
        when(postService.save(any(Post.class))).thenReturn(post);
        String postJson = new ObjectMapper().writeValueAsString(post);

        ResultActions result = mockMvc.perform(post("/api/posts/new")
                .contentType("application/json")
                .content(postJson)
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("test title"))
                .andExpect(jsonPath("$.content").value("test content"));
    }

    @Test
    void successfullyUpdatePostTest() throws Exception {
        Post post = new Post(0L, "test title", "test content");
        when(postService.edit(any(Post.class), eq(0L))).thenReturn(post);
        String postJson = new ObjectMapper().writeValueAsString(post);

        ResultActions result = mockMvc.perform(put("/api/posts/0")
                .contentType("application/json")
                .content(postJson)
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test title"))
                .andExpect(jsonPath("$.content").value("test content"));
    }

    @Test
    void successfullyDeletePostTest() throws Exception {
        Post post = new Post("test title", "test content");
        when(postService.delete(0L)).thenReturn(true);

        ResultActions result = mockMvc.perform(delete("/api/posts/0"));

        result.andExpect(status().isOk());
    }



}
