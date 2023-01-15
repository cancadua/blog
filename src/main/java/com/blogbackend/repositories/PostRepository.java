package com.blogbackend.repositories;

import com.blogbackend.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByOrderByCreatedAtDesc(Pageable pageable);
    @Transactional
    Integer deletePostByPostId(Long post_id);
}
