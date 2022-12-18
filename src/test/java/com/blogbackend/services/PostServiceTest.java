package com.blogbackend.services;

import com.blogbackend.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PostServiceTest {

    @MockBean
    private PostRepository postRepository;

    @Test
    void getAllPosts(){
        Post newPost = new Post(1L, "test title", "test content");
        postRepository.save(newPost);
    }
}