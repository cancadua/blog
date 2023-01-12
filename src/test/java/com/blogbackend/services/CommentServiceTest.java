package com.blogbackend.services;

import com.blogbackend.models.Comment;
import com.blogbackend.models.Comment;
import com.blogbackend.repositories.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @AfterEach
    void tearDown(){
        commentRepository.deleteAll();
    }

    @Test
    void getCommentsTest(){
        Comment newComment = new Comment("test content");
        Comment newComment2 = new Comment("test content2");
        Comment newComment3 = new Comment("test content3");
        CommentService commentService = new CommentService(commentRepository);
        commentService.save(newComment);
        commentService.save(newComment2);
        commentService.save(newComment3);

        assertEquals(3, commentService.findAll().size());
    }

    @Test
    void saveCommentTest() {
        CommentService commentService = new CommentService(commentRepository);
        Comment comment = new Comment("content");

        commentService.save(comment);

        assertEquals(1.0, commentRepository.count());
    }

    @Test
    void editCommentTest() {
        Comment newComment = new Comment("test content");
        commentRepository.save(newComment);
        CommentService commentService = new CommentService(commentRepository);
        Comment editedComment = new Comment("test content edit");
        Comment lastComment = commentService.findAll().get(0);

        commentService.edit(editedComment, lastComment.getCommentId());
        Comment lastCommentEdited = commentService.findAll().get(0);

        assertNotEquals(newComment.getContent(), lastCommentEdited.getContent());
    }

    @Test
    void deleteCommentTest() {
        Comment newComment = new Comment("test content");
        commentRepository.save(newComment);
        CommentService commentService = new CommentService(commentRepository);

        Comment lastComment = commentService.findAll().get(0);
        commentService.delete(lastComment.getCommentId());

        assertEquals(0, commentService.findAll().size());
    }
}
