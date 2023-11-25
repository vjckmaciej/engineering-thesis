package com.engineeringthesis.commons.dto.forum;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumUserDTO {
    private Long forumUserId;

    @NotBlank(message = "Username must not be blank!")
    @Size(min = 5, max = 20, message = "Username must contain minimum of 5 characters and maximum of 20 characters!")
    private String username;

    @NotBlank(message = "Password must not be blank!")
    @Size(min = 5, max = 20, message = "Password must contain minimum of 5 characters and maximum of 20 characters!")
    private String password;

    @NotBlank(message = "Email must not be blank!")
    @Email(message = "Email does not match the email pattern!")
    private String email;

    @Pattern(regexp = "\\d{11}", message = "PESEL must contain 11 digits!")
    private String pesel;

    @NotNull(message = "Registration date must not be null!")
    private LocalDate registrationDate;
}
