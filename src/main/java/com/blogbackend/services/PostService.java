package com.blogbackend.services;

import com.blogbackend.models.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    public List<Post> findAll() {
        return new ArrayList<Post>();
    }
}
