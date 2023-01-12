package com.blogbackend.services;

import com.blogbackend.models.Post;
import com.blogbackend.repositories.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
    }

    @Test
    void getAllPostsTest(){

        Post newPost = new Post("test title", "test content");
        Post newPost2 = new Post("test title2", "test content2");
        Post newPost3 = new Post("test title3", "test content3");
        PostService postService = new PostService(postRepository);
        postService.save(newPost);
        postService.save(newPost2);
        postService.save(newPost3);

        assertEquals(3, postService.findAll().size());
    }

    @Test
    void getOnePostTest(){
        Post newPost = new Post("test title", "test content");
        PostService postService = new PostService(postRepository);
        postService.save(newPost);

        Post existingPost = postService.findAll().get(0);
        Post existingPostById = postService.getPostById(existingPost.getPostId());


        assertEquals(newPost.getTitle(), existingPostById.getTitle());
        assertEquals(newPost.getContent(), existingPostById.getContent());
    }

    @Test
    void savePostTest() {
        PostService postService = new PostService(postRepository);
        Post post = new Post("title","content");

        postService.save(post);

        assertEquals(1.0, postRepository.count());
    }

    @Test
    void editPostTest() {
        Post newPost = new Post("test title", "test content");
        postRepository.save(newPost);
        PostService postService = new PostService(postRepository);
        Post editedPost = new Post("test title edit", "test content edit");
        Post lastPost = postService.findAll().get(0);

        postService.edit(editedPost, lastPost.getPostId());
        Post lastPostEdited = postService.findAll().get(0);

        assertNotEquals(newPost.getTitle(), lastPost.getTitle());
        assertNotEquals(newPost.getContent(), lastPost.getContent());
    }

    @Test
    void deletePostTest() {
        Post newPost = new Post("test title", "test content");
        postRepository.save(newPost);
        PostService postService = new PostService(postRepository);

        Post lastPost = postService.findAll().get(0);
        postService.delete(lastPost);

        assertEquals(0, postService.findAll().size());
    }
}