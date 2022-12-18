package com.blogbackend.controllers;

import com.blogbackend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/public/posts")
    public ResponseEntity<?> getPosts() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }
}
