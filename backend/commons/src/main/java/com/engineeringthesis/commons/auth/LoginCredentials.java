package com.engineeringthesis.commons.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredentials {
    @NotBlank(message = "Username must not be blank!")
    @Size(min = 5, max = 20, message = "Username must contain minimum of 5 characters and maximum of 20 characters!")
    private String username;

    @NotBlank(message = "Password must not be blank!")
    @Size(min = 5, max = 20, message = "Password must contain minimum of 5 characters and maximum of 20 characters!")
    private String password;
}
