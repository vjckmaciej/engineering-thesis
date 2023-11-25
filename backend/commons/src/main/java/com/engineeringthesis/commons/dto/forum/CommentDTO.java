package com.engineeringthesis.commons.dto.forum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long commentId;

    @NotBlank(message = "Content must not be blank!")
    @Size(min = 3, max = 1000, message = "Content must contain minimum of 3 characters and maximum of 1000 characters!")
    private String content;

    @NotNull(message = "ThreadID must not be null!")
    @Positive(message = "ThreadID must be positive!")
    private Long threadId;

    @NotNull(message = "AuthorID must not be null!")
    @Positive(message = "AuthorID must be positive!")
    private Long authorId;
}
