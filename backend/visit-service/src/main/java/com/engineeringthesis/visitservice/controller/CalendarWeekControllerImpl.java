package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.visitservice.client.UserServiceClient;
import com.engineeringthesis.visitservice.entity.CalendarWeek;
import com.engineeringthesis.visitservice.mapper.CalendarWeekMapper;
import com.engineeringthesis.visitservice.service.CalendarWeekServiceImpl;
import com.engineeringthesis.commons.dto.calendardietplan.CalendarWeekDTO;
import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/calendarWeek")
public class CalendarWeekControllerImpl implements CrudController<CalendarWeekDTO> {
    private final CalendarWeekServiceImpl calendarWeekService;
    private final CalendarWeekMapper calendarWeekMapper;

    @Autowired
    private UserServiceClient userServiceClient;


    @Override
    public ResponseEntity<CrudResponse> add(CalendarWeekDTO calendarWeekDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CalendarWeekDTO> getById(Long id) {
        return null;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CalendarWeekDTO>> getAll() {
        log.info("Starting getting list of all CalendarWeek objects");
        List<CalendarWeek> allCalendarWeeks = calendarWeekService.getAll();
        List<CalendarWeekDTO> allCalendarWeekDTOS = allCalendarWeeks.stream().map((calendarWeekMapper::calendarWeekToCalendarWeekDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allCalendarWeekDTOS);
    }

    @RequestMapping(path = "/pesel/{pesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalendarWeekDTO> getCalendarWeekByPesel(@PathVariable String pesel) {
        log.info("Starting getting Calendar week for Patient with PESEL: " + pesel);
        Integer pregnancyWeek = userServiceClient.getPregnancyWeekByPesel(pesel);
        CalendarWeek calendarWeek = calendarWeekService.findCalendarWeekByPregnancyWeek(pregnancyWeek);
        CalendarWeekDTO calendarWeekDTO = calendarWeekMapper.calendarWeekToCalendarWeekDTO(calendarWeek);
        return ResponseEntity.ok(calendarWeekDTO);
    }

    @RequestMapping(path = "/getCalendarWeek/{pregnancyWeek}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalendarWeekDTO> getCalendarWeekByPregnancyWeek(@PathVariable Integer pregnancyWeek) {
        log.info("Starting getting Calendar week for pregnany week: " + pregnancyWeek);
        CalendarWeek calendarWeek = calendarWeekService.findCalendarWeekByPregnancyWeek(pregnancyWeek);
        CalendarWeekDTO calendarWeekDTO = calendarWeekMapper.calendarWeekToCalendarWeekDTO(calendarWeek);
        return ResponseEntity.ok(calendarWeekDTO);
    }

    @Override
    public ResponseEntity<CrudResponse> update(CalendarWeekDTO calendarWeekDTO) {
        return null;
    }

    @Override
    public ResponseEntity<CrudResponse> deleteById(Long id) {
        return null;
    }
}
