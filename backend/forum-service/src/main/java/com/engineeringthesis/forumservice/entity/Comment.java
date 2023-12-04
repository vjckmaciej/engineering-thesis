package com.engineeringthesis.forumservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commentId", nullable = false)
    private Long commentId;

    @Column
    private String content;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column
    private Long threadIdReference;

    @ManyToOne
    @JoinColumn(name = "threadId")
    private Thread thread;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private ForumUser author;
}
