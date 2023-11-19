package com.engineeringthesis.forumservice.mapper;

import com.engineeringthesis.commons.dto.forum.ForumUserDTO;
import com.engineeringthesis.forumservice.entity.ForumUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ForumUserMapperImpl implements ForumUserMapper {
    @Override
    public ForumUser forumUserDTOToForumUser(ForumUserDTO forumUserDTO) {
        if (forumUserDTO == null) {
            return null;
        }

        ForumUser forumUser = new ForumUser();

        forumUser.setForumUserId(forumUserDTO.getForumUserId());
        forumUser.setUsername(forumUserDTO.getUsername());
        forumUser.setPassword(forumUserDTO.getPassword());
        forumUser.setEmail(forumUserDTO.getEmail());
        forumUser.setPesel(forumUserDTO.getPesel());

        return forumUser;
    }

    @Override
    public ForumUserDTO forumUserToForumUserDTO(ForumUser forumUser) {
        if (forumUser == null) {
            return null;
        }

        ForumUserDTO forumUserDTO = new ForumUserDTO();

        forumUserDTO.setForumUserId(forumUser.getForumUserId());
        forumUserDTO.setUsername(forumUser.getUsername());
        forumUserDTO.setPassword(forumUser.getPassword());
        forumUserDTO.setEmail(forumUser.getEmail());
        forumUserDTO.setPesel(forumUser.getPesel());
        forumUserDTO.setRegistrationDate(forumUser.getRegistrationDate());

        return forumUserDTO;
    }
}
