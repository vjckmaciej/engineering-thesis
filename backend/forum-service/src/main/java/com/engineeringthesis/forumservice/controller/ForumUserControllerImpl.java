package com.engineeringthesis.forumservice.controller;

import com.engineeringthesis.commons.auth.LoginCredentials;
import com.engineeringthesis.commons.dto.forum.ForumUserDTO;
import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.forumservice.mapper.ForumUserMapper;
import com.engineeringthesis.forumservice.entity.ForumUser;
import com.engineeringthesis.forumservice.service.ForumUserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/forumUser")
public class ForumUserControllerImpl implements CrudController<ForumUserDTO> {
    private final ForumUserServiceImpl forumUserService;
    private final ForumUserMapper forumUserMapper;

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@Valid @RequestBody ForumUserDTO forumUserDTO) {
        Long forumUserId = forumUserDTO.getForumUserId();
        log.info("Starting saving ForumUser with forumUserId: " + forumUserId);
        ForumUser forumUser = forumUserMapper.forumUserDTOToForumUser(forumUserDTO);
        forumUser.setRegistrationDate(LocalDate.now());
        forumUserService.save(forumUser);
        return ResponseEntity.ok(new CrudResponse(forumUser.getForumUserId(),"Forum user added to database!"));
    }

    @Override
    @RequestMapping(path = "/{forumUserId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ForumUserDTO> getById(@PathVariable Long forumUserId) {
        log.info("Starting finding ForumUser with forumUserId: " + forumUserId);
        ForumUser forumUser = forumUserService.getById(forumUserId);
        ForumUserDTO forumUserDTO = forumUserMapper.forumUserToForumUserDTO(forumUser);
        return ResponseEntity.ok(forumUserDTO);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ForumUserDTO>> getAll() {
        log.info("Starting getting list of all ForumUser objects");
        List<ForumUser> allForumUsers = forumUserService.getAll();
        List<ForumUserDTO> allForumUsersDTOS = allForumUsers.stream().map((forumUserMapper::forumUserToForumUserDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allForumUsersDTOS);
    }

    @RequestMapping(path="/getForumUserIdByPesel/{pesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long getForumUserIdByPesel(@PathVariable String pesel) {
        log.info("Starting getting authorID by PESEL: " + pesel);
        return forumUserService.getForumUserIdByPesel(pesel);
    }

    @RequestMapping(path="/getForumUserUsernameByAuthorId/{authorId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getForumUserUsernameByAuthorId(@PathVariable Long authorId) {
        log.info("Starting getting forum user username by authorId: " + authorId);
        return forumUserService.getForumUserUsernameByAuthorId(authorId);
    }

    @RequestMapping(path="/getForumUserUsernameByPesel/{pesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getForumUserUsernameByPesel(@PathVariable String pesel) {
        log.info("Starting getting forum user username by PESEL: " + pesel);
        return forumUserService.getForumUserUsernameByPesel(pesel);
    }

    @RequestMapping(path="/getForumUserPeselByUsername/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getForumUserPeselByUsername(@PathVariable String username) {
        log.info("Starting getting forum user pesel by username: " + username);
        return forumUserService.getForumUserPeselByUsername(username);
    }

    @RequestMapping(path = "/existsByUsername/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean existsByUsername(@PathVariable String username) {
        log.info("Checking if forum user exists by given username: " + username);
        return forumUserService.existsByUsername(username);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> update(@RequestBody ForumUserDTO forumUserDTO) {
        Long forumUserId = forumUserDTO.getForumUserId();
        log.info(String.format("Starting ForumUser update with forumUserId: %d", forumUserId));
        ForumUser forumUser = forumUserMapper.forumUserDTOToForumUser(forumUserDTO);
        forumUserService.update(forumUser);
        return ResponseEntity.ok(new CrudResponse(forumUserId, "ForumUser updated!"));
    }

    @Override
    @RequestMapping(path = "/{forumUserId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> deleteById(@PathVariable Long forumUserId) {
        log.info(String.format("Starting deleting ForumUser by forumUserId: %d", forumUserId));
        forumUserService.deleteById(forumUserId);
        String message = String.format("ForumUser with forumUserId: %d deleted!", forumUserId);
        return ResponseEntity.ok(new CrudResponse(forumUserId, message));
    }

    @RequestMapping(path="/checkCredentials", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean areLoginCredentialsCorrect(@RequestBody LoginCredentials loginCredentials) {
        log.info("Starting checking credentials for forum user with username: " + loginCredentials.getUsername());
        return forumUserService.checkCredentials(loginCredentials);
    }
}
