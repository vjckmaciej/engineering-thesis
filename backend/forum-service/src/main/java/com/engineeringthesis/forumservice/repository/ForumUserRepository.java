package com.engineeringthesis.forumservice.repository;

import com.engineeringthesis.forumservice.entity.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForumUserRepository extends JpaRepository<ForumUser, Long> {
        ForumUser save(ForumUser forumUser);
//
    Optional<ForumUser> findByForumUserId(Long forumId);
//
//    Optional<ForumUser> findByPesel(String pesel);
//
    boolean existsByForumUserId(Long forumId);
//
    boolean existsByPesel(String PESEL);
//
    List<ForumUser> findAll();
//
    List<ForumUser> deleteByForumUserId(Long forumId);
//
//    void deleteByPesel(String pesel);
}
