package com.blogbackend.services;

import com.blogbackend.error.NotFoundException;
import com.blogbackend.error.UnauthorizedException;
import com.blogbackend.models.Post;
import com.blogbackend.models.User;
import com.blogbackend.models.UserDetailsImpl;
import com.blogbackend.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post getPostById(Long post_id) {
        return postRepository.findById(post_id).orElseThrow(
                () -> new NotFoundException("No post with id " + post_id)
        );
    }

    public Page<Post> getPages(Integer page) {
        Pageable pages = PageRequest.of(page, 10);
        return postRepository.findByOrderByCreatedAtDesc(pages);
    }

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {
        UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (post.getPostId() == null) {
            post.setCreatedAt(LocalDateTime.now());
            post.setUser(new User(currentUser.getUserId()));
        }

        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public boolean delete(Long post_id) {
        Post existingPost = getById(post_id).orElseThrow(
                () -> new NotFoundException("No post with id " + post_id)
        );
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth.getName().equals(existingPost.getUser().getUsername()) ||
                auth.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority()
                                .equals("ROLE_ADMIN"))) {
            return postRepository.deletePostByPostId(post_id) > 0;

        } else throw new UnauthorizedException("Can't delete posts of user " + existingPost.getUser().getUsername());
    }

    public Post edit(Post post, Long post_id) {
        Post existingPost = getById(post_id).orElseThrow(
                () -> new NotFoundException("No post with id " + post_id)
        );
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth.getName().equals(existingPost.getUser().getUsername()) ||
                auth.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority()
                                .equals("ROLE_ADMIN"))) {
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            return save(existingPost);
        } else throw new UnauthorizedException("Can't edit posts of user " + existingPost.getUser().getUsername());
    }

    public Optional<Post> getById(Long post_id) {
        return postRepository.findById(post_id);
    }
}
