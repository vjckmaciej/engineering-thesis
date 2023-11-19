package com.engineeringthesis.commons.dto.forum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumUserDTO {
    private Long forumUserId;
    private String username;
    private String password;
    private String email;
    private String pesel;
    private LocalDate registrationDate;
}
