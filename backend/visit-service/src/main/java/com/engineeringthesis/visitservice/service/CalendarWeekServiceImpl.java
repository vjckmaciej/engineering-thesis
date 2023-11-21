package com.engineeringthesis.visitservice.service;

import com.engineeringthesis.visitservice.entity.CalendarWeek;
import com.engineeringthesis.visitservice.repository.CalendarWeekRepository;
import com.engineeringthesis.commons.model.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public CalendarWeek findCalendarWeekByPregnancyWeek(Integer pregnancyWeek) {
        String message = String.format("CalendarWeek with this pregnancyWeek: %d doesn't exist in database!", pregnancyWeek);
        return calendarWeekRepository.findByPregnancyWeek(pregnancyWeek).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, message));
    }

    @Override
    public void deleteById(Long id) {

    }
}
