package com.engineeringthesis.commons.dto.calendardietplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarWeekDTO {
    private Long calendarWeekId;
    private Integer pregnancyWeek;
    private String description;
}
