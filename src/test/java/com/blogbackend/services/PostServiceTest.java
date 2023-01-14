package com.blogbackend.services;

import com.blogbackend.models.Post;
import com.blogbackend.models.Post;
import com.blogbackend.repositories.PostRepository;
import com.blogbackend.repositories.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    PostService postService;

    @Mock
    PostRepository postRepository;

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
    }

    @Test
    void getAllPostsTest(){
        List<Post> list = new ArrayList<Post>();
        Post post = new Post("title","content");
        Post post2 = new Post("title2","content2");
        Post post3 = new Post("title3","content3");
        list.add(post);
        list.add(post2);
        list.add(post3);

        when(postRepository.findAll()).thenReturn(list);
        List<Post> postList = postService.findAll();

        assertEquals(3, postList.size());
    }

    @Test
    void getOnePostTest(){
        Post post = new Post(0L, "title","content");
        Optional<Post> optionalPost = Optional.of(post);

        when(postRepository.findById(any(Long.class))).thenReturn(optionalPost);
        Post returnedPost = postService.getPostById(0L);

        assertEquals(post.getContent(), returnedPost.getContent());
    }

    @Test
    void savePostTest() {
        Post post = new Post("title","content");

        when(postRepository.save(any(Post.class))).thenReturn(post);
        Post createdPost = postService.save(post);

        assertEquals(post.getContent(), createdPost.getContent());
    }

    @Test
    void editPostTest() {
        Post post = new Post(0L, "title", "content");
        Optional<Post> optionalPost = Optional.of(post);

        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postRepository.findById(any(Long.class))).thenReturn(optionalPost);
        Post editedPost = postService.edit(post, post.getPostId());

        assertEquals(post.getContent(), editedPost.getContent());
    }

    @Test
    void deletePostTest() {
        Post post = new Post(0L, "title", "content");

        when(postRepository.deletePostByPostId(any(Long.class))).thenReturn(true);
        boolean isDeleted = postService.delete(post.getPostId());

        assertTrue(isDeleted);
    }
}