package com.blogbackend.services;

import com.blogbackend.models.Post;
import com.blogbackend.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void delete(Post lastPost) {
    }

    public void edit(Post editedPost, Long i) {
    }

    public Post getPostById(Long postId) {
        return new Post();
    }
}
