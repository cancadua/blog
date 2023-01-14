package com.blogbackend.repositories;

import com.blogbackend.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    boolean deletePostByPostId(Long post_id);
}
