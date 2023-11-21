package com.engineeringthesis.visitservice.mapper;

import com.engineeringthesis.visitservice.entity.CalendarWeek;
import com.engineeringthesis.commons.dto.calendardietplan.CalendarWeekDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CalendarWeekMapperImpl implements CalendarWeekMapper{

    @Override
    public CalendarWeek calendarWeekDTOToCalendarWeek(CalendarWeekDTO calendarWeekDTO) {
        if (calendarWeekDTO == null) {
            return null;
        }

        CalendarWeek calendarWeek = new CalendarWeek();

        calendarWeek.setCalendarWeekId(calendarWeekDTO.getCalendarWeekId());
        calendarWeek.setPregnancyWeek(calendarWeekDTO.getPregnancyWeek());
        calendarWeek.setDescription(calendarWeekDTO.getDescription());

        return calendarWeek;
    }

    @Override
    public CalendarWeekDTO calendarWeekToCalendarWeekDTO(CalendarWeek calendarWeek) {
        if (calendarWeek == null) {
            return null;
        }

        CalendarWeekDTO calendarWeekDTO = new CalendarWeekDTO();

        calendarWeekDTO.setCalendarWeekId(calendarWeek.getCalendarWeekId());
        calendarWeekDTO.setPregnancyWeek(calendarWeek.getPregnancyWeek());
        calendarWeekDTO.setDescription(calendarWeek.getDescription());

        return calendarWeekDTO;
    }
}
