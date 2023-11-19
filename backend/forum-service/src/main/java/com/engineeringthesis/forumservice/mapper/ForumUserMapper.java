package com.engineeringthesis.forumservice.mapper;

import com.engineeringthesis.commons.dto.forum.ForumUserDTO;
import com.engineeringthesis.forumservice.entity.ForumUser;
import org.mapstruct.Mapper;

@Mapper
public interface ForumUserMapper {
    ForumUser forumUserDTOToForumUser(ForumUserDTO forumUserDTO);

    ForumUserDTO forumUserToForumUserDTO(ForumUser forumUser);
}
