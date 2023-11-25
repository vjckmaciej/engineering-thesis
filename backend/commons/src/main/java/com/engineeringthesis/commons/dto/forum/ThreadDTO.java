package com.engineeringthesis.commons.dto.forum;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreadDTO {
    private Long threadId;

    @NotBlank(message = "Category must not be blank!")
    @Size(max = 100, message = "Category must contain maximum of 100 characters!")
    private String category;

    @NotBlank(message = "Title must not be blank!")
    @Size(max = 100, message = "Title must contain maximum of 100 characters!")
    private String title;

    @NotBlank(message = "Content must not be blank!")
    @Size(max = 1000, message = "Content must contain maximum of 1000 characters!")
    private String content;

    @NotNull(message = "AuthorID must not be null!")
    @Positive(message = "AuthorID must be positive!")
    private Long authorId;
}
