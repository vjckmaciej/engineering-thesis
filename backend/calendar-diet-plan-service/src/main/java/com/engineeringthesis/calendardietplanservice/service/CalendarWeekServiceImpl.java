package com.engineeringthesis.calendardietplanservice.service;

import com.engineeringthesis.calendardietplanservice.entity.CalendarWeek;
import com.engineeringthesis.calendardietplanservice.repository.CalendarWeekRepository;
import com.engineeringthesis.commons.model.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarWeekServiceImpl implements CrudService<CalendarWeek> {
    private final CalendarWeekRepository calendarWeekRepository;


    @Override
    public void save(CalendarWeek calendarWeek) {

    }

    @Override
    public void update(CalendarWeek calendarWeek) {

    }

    @Override
    public CalendarWeek getById(Long id) {
        return null;
    }

    @Override
    public List<CalendarWeek> getAll() {
        return calendarWeekRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {

    }
}
