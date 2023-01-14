package com.blogbackend.services;

import com.blogbackend.error.NotFoundException;
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

    public boolean delete(Long post_id) {
        return postRepository.deletePostByPostId(post_id);
    }

    public Post edit(Post post, Long post_id) {
        Post existingPost = postRepository.findById(post_id).orElseThrow(
                () -> new NotFoundException("No post with id " + post_id)
        );

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        return save(existingPost);
    }

    public Post getPostById(Long post_id) {
        return postRepository.findById(post_id).orElseThrow(
                () -> new NotFoundException("No post with id " + post_id)
        );
    }
}
