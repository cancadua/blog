package com.blogbackend.controllers;

import com.blogbackend.models.Post;
import com.blogbackend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "Number-Of-Pages")
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/public/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping("/public/posts/page={page}")
    public ResponseEntity<List<Post>> getPosts(@PathVariable Integer page) {
        Page<Post> postsPage = postService.getPages(page);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Number-Of-Pages", String.valueOf(postsPage.getTotalPages()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(postsPage.stream().toList());
    }

    @GetMapping("/public/posts")
    public ResponseEntity<?> getPosts() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/posts/new")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createNewPost(@RequestBody Post post) {
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{post_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updatePost(@PathVariable Long post_id, @RequestBody Post post) {
        return new ResponseEntity<>(postService.edit(post, post_id), HttpStatus.OK);
    }


    @DeleteMapping("/posts/{post_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteMessage(@PathVariable Long post_id) {
        return new ResponseEntity<>(postService.delete(post_id), HttpStatus.OK);
    }
}
