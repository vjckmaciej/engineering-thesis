package com.engineeringthesis.forumservice.mapper;

import com.engineeringthesis.commons.dto.forum.ThreadDTO;
import com.engineeringthesis.forumservice.entity.Thread;
import org.mapstruct.Mapper;

@Mapper
public interface ThreadMapper {
    Thread threadDTOToThread(ThreadDTO threadDTO);

    ThreadDTO threadToThreadDTO(Thread thread);
}
