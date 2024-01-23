package com.engineeringthesis.forumservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "THREADS")
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "threadId", nullable = false)
    private Long threadId;

    @Column
    private String category;

    @Column
    private String title;

    @Column
    private String content;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column
    private Long authorIdReference;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private ForumUser author;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
    private List<Comment> comments;
}


