package com.blogbackend.models;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Post (Long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

    public Post (String title, String content) {
        this.title = title;
        this.content = content;
    }
}
