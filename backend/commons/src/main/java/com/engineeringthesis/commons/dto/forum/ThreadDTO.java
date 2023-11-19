package com.engineeringthesis.commons.dto.forum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreadDTO {
    private Long threadId;
    private String category;
    private String title;
    private String content;
    private Long authorId;
}
