package com.engineeringthesis.visitservice.controller;

import com.engineeringthesis.visitservice.client.UserServiceClient;
import com.engineeringthesis.visitservice.entity.CalendarWeek;
import com.engineeringthesis.visitservice.mapper.CalendarWeekMapper;
import com.engineeringthesis.visitservice.service.CalendarWeekServiceImpl;
import com.engineeringthesis.commons.dto.calendardietplan.CalendarWeekDTO;
import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@Valid @RequestBody CalendarWeekDTO calendarWeekDTO) {
        Long calendarWeekId = calendarWeekDTO.getCalendarWeekId();
        log.info("Starting saving CalendarWeek with calendarWeekId: " + calendarWeekId);
        CalendarWeek calendarWeekToSave = calendarWeekMapper.calendarWeekDTOToCalendarWeek(calendarWeekDTO);
        calendarWeekService.save(calendarWeekToSave);
        return ResponseEntity.ok(new CrudResponse(calendarWeekToSave.getCalendarWeekId(), "CalendarWeek added to database!"));

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
    @RequestMapping(path = "/{calendarWeekId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> deleteById(@PathVariable Long calendarWeekId) {
        log.info(String.format("Starting deleting CalendarWeek by calendarWeekId: %d", calendarWeekId));
        calendarWeekService.deleteById(calendarWeekId);
        String message = String.format("CalendarWeek with calendarWeekId: %d deleted!", calendarWeekId);
        return ResponseEntity.ok(new CrudResponse(calendarWeekId, message));
    }
}
