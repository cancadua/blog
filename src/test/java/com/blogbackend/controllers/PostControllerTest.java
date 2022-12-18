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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void getAllPostsTest() throws Exception {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "test title", "test content"));
        posts.add(new Post(2L, "test title2", "test content2"));
        when(postService.findAll()).thenReturn(posts);

        mockMvc.perform(get("/api/public/posts")
                .contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void successfullyCreateAPost() throws Exception {
        Post post = new Post("test title", "test content");
        when(postService.save(post).thenReturn(post));
        String postJson = new ObjectMapper().writeValueAsString(post);
        ResultActions result = mockMvc.perform(post("/api/posts/new")
                .contentType("application/json")
                .content(postJson)
        );

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("test title"))
                .andExpect(jsonPath("$.content").value("test content"));
    }

}
