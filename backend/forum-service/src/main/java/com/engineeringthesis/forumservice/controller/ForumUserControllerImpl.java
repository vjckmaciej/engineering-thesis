package com.engineeringthesis.forumservice.controller;

import com.engineeringthesis.commons.dto.forum.ForumUserDTO;
import com.engineeringthesis.commons.dto.user.PatientDTO;
import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.forumservice.client.UserServiceClient;
import com.engineeringthesis.forumservice.mapper.ForumUserMapper;
import com.engineeringthesis.forumservice.entity.ForumUser;
import com.engineeringthesis.forumservice.service.ForumUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/forumUser")
public class ForumUserControllerImpl implements CrudController<ForumUserDTO> {
    private final ForumUserServiceImpl forumUserService;
    private final ForumUserMapper forumUserMapper;

    @Autowired
    private UserServiceClient userServiceClient;


    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@RequestBody ForumUserDTO forumUserDTO) {
        Long forumUserId = forumUserDTO.getForumUserId();
        log.info("Starting saving ForumUser with forumUserId: " + forumUserId);

        String forumUserPesel = forumUserDTO.getPesel();

        try {
            ResponseEntity<PatientDTO> optionalPatientDTO = userServiceClient.getByPesel(forumUserPesel);
            if (optionalPatientDTO.getStatusCode().equals(HttpStatus.OK)) {
                ForumUser forumUser = forumUserMapper.forumUserDTOToForumUser(forumUserDTO);
                forumUserService.save(forumUser);
                return ResponseEntity.ok(new CrudResponse(forumUser.getForumUserId(), "ForumUser added to database!"));
            } else {
                String message = String.format("ForumUser with this PESEL: %s already exists in database!", forumUserPesel);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            log.error(message);
            CrudResponse crudResponse = new CrudResponse(forumUserId, message);
            return new ResponseEntity<>(crudResponse, HttpStatus.NOT_FOUND);
        }
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
}
