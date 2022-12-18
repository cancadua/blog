package com.blogbackend.services;

import com.blogbackend.models.Post;
import com.blogbackend.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void getAllPostsTest(){

        Post newPost = new Post("test title", "test content");
        postRepository.save(newPost);
        PostService postService = new PostService(postRepository);

        Post lastPost = postService.findAll().get(0);

        assertEquals(newPost.getTitle(), lastPost.getTitle());
        assertEquals(newPost.getContent(), lastPost.getContent());
    }
}