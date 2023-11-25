package com.engineeringthesis.commons.dto.calendardietplan;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarWeekDTO {
    private Long calendarWeekId;

    @NotNull(message = "Pregnancy week must not be null!")
    @Min(value = 1, message = "Pregnancy week must be minimum 1!")
    @Max(value = 42, message = "Pregnancy week must be maximum 42!")
    private Integer pregnancyWeek;

    @NotBlank(message = "Description must not be blank!")
    @Size(min = 10, message = "Description must contain minimum of 10 characters!")
    private String description;
}
