package com.engineeringthesis.forumservice.repository;

import com.engineeringthesis.forumservice.entity.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForumUserRepository extends JpaRepository<ForumUser, Long> {
    ForumUser save(ForumUser forumUser);

    Optional<ForumUser> findByForumUserId(Long forumId);

    Optional<ForumUser> findForumUserByUsername(String username);

    boolean existsByForumUserId(Long forumId);

    boolean existsByPesel(String PESEL);

    boolean existsByUsername(String username);

    List<ForumUser> findAll();

    @Query("SELECT fu.forumUserId FROM ForumUser fu WHERE fu.pesel = :pesel")
    Long findForumUserIdByPesel(@Param("pesel") String pesel);

    @Query("SELECT fu.username FROM ForumUser fu WHERE fu.forumUserId = :forumUserId")
    String findUsernameByForumUserId(@Param("forumUserId") Long forumUserId);

    @Query("SELECT fu.username FROM ForumUser fu WHERE fu.pesel = :pesel")
    String findUsernameByPesel(@Param("pesel") String pesel);

    @Query("SELECT fu.pesel FROM ForumUser fu WHERE fu.username = :username")
    String findPeselByUsername(@Param("username") String username);

    List<ForumUser> deleteByForumUserId(Long forumId);
}

