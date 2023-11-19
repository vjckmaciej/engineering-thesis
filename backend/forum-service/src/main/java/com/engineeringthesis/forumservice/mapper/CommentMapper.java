package com.engineeringthesis.forumservice.mapper;

import com.engineeringthesis.commons.dto.forum.CommentDTO;
import com.engineeringthesis.forumservice.entity.Comment;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {
    Comment commentDTOToComment(CommentDTO commentDTO);

    CommentDTO commentToCommentDTO(Comment comment);
}
