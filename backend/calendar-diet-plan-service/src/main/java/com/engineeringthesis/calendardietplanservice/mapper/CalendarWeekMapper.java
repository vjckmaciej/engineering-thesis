package com.engineeringthesis.calendardietplanservice.mapper;

import com.engineeringthesis.calendardietplanservice.entity.CalendarWeek;
import com.engineeringthesis.commons.dto.calendardietplan.CalendarWeekDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CalendarWeekMapper {
    CalendarWeek calendarWeekDTOToCalendarWeek(CalendarWeekDTO calendarWeekDTO);

    CalendarWeekDTO calendarWeekToCalendarWeekDTO(CalendarWeek calendarWeek);
}
