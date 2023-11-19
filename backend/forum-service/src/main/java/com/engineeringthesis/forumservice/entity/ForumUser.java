package com.engineeringthesis.forumservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FORUM_USERS")
public class ForumUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "forumUserId", nullable = false)
    private Long forumUserId;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String pesel;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "author")
    private List<Thread> threads;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;
}
