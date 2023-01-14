package com.blogbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotBlank
    private String content;

    @Column(name = "created_date", nullable = false, updatable = false)
    @NotNull
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    @NotNull
    private Post post;

    public Comment(String content) {
        this.content = content;
    }

    public Comment(Long comment_id, String content) {
        this.commentId = comment_id;
        this.content = content;
    }
}
