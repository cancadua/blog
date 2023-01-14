package com.blogbackend.repositories;

import com.blogbackend.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    boolean deleteCommentByCommentId(Long post_id);

    List<Comment> findCommentsByPostOrderByCreatedAtAsc(Long any);
}
