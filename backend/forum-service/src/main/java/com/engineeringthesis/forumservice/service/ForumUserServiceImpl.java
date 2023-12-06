package com.engineeringthesis.forumservice.service;

import com.engineeringthesis.commons.exception.DataSaveException;
import com.engineeringthesis.commons.exception.DeleteException;
import com.engineeringthesis.commons.model.CrudService;
import com.engineeringthesis.forumservice.entity.ForumUser;
import com.engineeringthesis.forumservice.repository.ForumUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForumUserServiceImpl implements CrudService<ForumUser> {
    private final ForumUserRepository forumUserRepository;


    @Override
    public void save(ForumUser forumUser) {
        Long forumUserId = forumUser.getForumUserId();
        String forumUserPesel = forumUser.getPesel();

        try {
            if (forumUserRepository.existsByForumUserId(forumUserId) || forumUserRepository.existsByPesel(forumUserPesel)) {
                String message = String.format("ForumUser with this forumUserId: %d or PESEL: %s already exists in database!", forumUserId, forumUserPesel);
                log.error(message);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }

            forumUser.setRegistrationDate(LocalDate.now());
            forumUserRepository.save(forumUser);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save ForumUser with this forumUserId: %d", forumUserId);
            throw new DataSaveException(message);
        }
    }

    @Override
    public ForumUser getById(Long id) {
        String message = String.format("ForumUser with this forumUserId: %d doesn't exist in database!", id);
        return forumUserRepository.findByForumUserId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public List<ForumUser> getAll() {
        return forumUserRepository.findAll();
    }

    public Long getForumUserIdByPesel(String pesel) {
        return forumUserRepository.findForumUserIdByPesel(pesel);
    }

    @Override
    public void update(ForumUser forumUser) {
        Long forumUserId = forumUser.getForumUserId();
        try {
            ForumUser oldForumUser = forumUserRepository.findByForumUserId(forumUserId).orElseThrow(() -> {
                String message = String.format("%s with this forumUserId: %d doesn't exist in database!", forumUser.getClass().getName(), forumUserId);
//                return new EntityIdDoesNotExistException(message);
                return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            });

            forumUser.setForumUserId(oldForumUser.getForumUserId());

            if (forumUser.getUsername() != null && !Objects.equals(forumUser.getUsername(), oldForumUser.getUsername())) {
                oldForumUser.setUsername(forumUser.getUsername());
            }

            if (forumUser.getPassword() != null && !Objects.equals(forumUser.getPassword(), oldForumUser.getPassword())) {
                oldForumUser.setPassword(forumUser.getPassword());
            }

            if (forumUser.getEmail() != null && !Objects.equals(forumUser.getEmail(), oldForumUser.getEmail())) {
                oldForumUser.setEmail(forumUser.getEmail());
            }

            if (forumUser.getPesel() != null && !Objects.equals(forumUser.getPesel(), oldForumUser.getPesel())) {
                oldForumUser.setPesel(forumUser.getPesel());
            }

            forumUserRepository.save(oldForumUser);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot save ForumUser with this forumUserId: %d", forumUserId);
            throw new DataSaveException(message);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            if (!forumUserRepository.existsByForumUserId(id)) {
                String message = String.format("Cannot delete ForumUser with forumUserId: %d! Object doesn't exist in database!", id);
                //            throw new DeleteException(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
            forumUserRepository.deleteByForumUserId(id);
        } catch (DataAccessException e) {
            log.error(e.getLocalizedMessage());
            String message = String.format("Cannot delete ForumUser with this forumUserId: %d", id);
            throw new DeleteException(message);
        }
    }
}
