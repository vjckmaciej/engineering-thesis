package com.engineeringthesis.userservice.controller;

import com.engineeringthesis.commons.model.CrudController;
import com.engineeringthesis.commons.model.CrudPESELResponse;
import com.engineeringthesis.commons.model.CrudResponse;
import com.engineeringthesis.commons.dto.user.DoctorDTO;
import com.engineeringthesis.userservice.entity.Doctor;
import com.engineeringthesis.userservice.mapper.DoctorMapper;
import com.engineeringthesis.userservice.service.DoctorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/doctor")
public class DoctorControllerImpl implements CrudController<DoctorDTO> {
    private final DoctorServiceImpl doctorService;
    private final DoctorMapper doctorMapper;

    @Override
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> add(@Valid @RequestBody DoctorDTO doctorDTO) {
        Long doctorId = doctorDTO.getDoctorId();
        log.info("Starting saving Doctor with doctorId: " + doctorId);
        Doctor doctor = doctorMapper.doctorDTOToDoctor(doctorDTO);
        doctor.setRegistryDate(LocalDate.now());
        doctorService.save(doctor);
        return ResponseEntity.ok(new CrudResponse(doctor.getDoctorId(), "Doctor added to database!"));
    }

    @Override
    @RequestMapping(path = "/id/{doctorId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorDTO> getById(@PathVariable Long doctorId) {
        log.info("Starting finding Doctor with doctorId: " + doctorId);
        Doctor doctor = doctorService.getById(doctorId);
        DoctorDTO doctorDTO = doctorMapper.doctorToDoctorDTO(doctor);
        return ResponseEntity.ok(doctorDTO);
    }

    @RequestMapping(path = "/pesel/{pesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorDTO> getByPesel(@PathVariable String pesel) {
        log.info("Starting finding Doctor with PESEL: " + pesel);
        Doctor doctor = doctorService.getByPesel(pesel);
        DoctorDTO doctorDTO = doctorMapper.doctorToDoctorDTO(doctor);
        return ResponseEntity.ok(doctorDTO);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DoctorDTO>> getAll() {
        log.info("Starting getting list of all Doctor objects");
        List<Doctor> allDoctors = doctorService.getAll();
        List<DoctorDTO> allDoctorDTOS = allDoctors.stream().map((doctorMapper::doctorToDoctorDTO)).collect(Collectors.toList());
        return ResponseEntity.ok(allDoctorDTOS);
    }

    @Override
    @RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> update(@RequestBody DoctorDTO doctorDTO) {
        Long doctorId = doctorDTO.getDoctorId();
        log.info(String.format("Starting Doctor update with doctorId: %d", doctorId));
        Doctor doctor = doctorMapper.doctorDTOToDoctor(doctorDTO);
        doctorService.update(doctor);
        return ResponseEntity.ok(new CrudResponse(doctorId, "Doctor updated!"));
    }

    @RequestMapping(path = "/pesel", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudPESELResponse> updateByPesel(@RequestBody DoctorDTO doctorDTO) {
        String PESEL = doctorDTO.getPesel();
        log.info(String.format("Starting Doctor update with PESEL: %s", PESEL));
        Doctor doctor = doctorMapper.doctorDTOToDoctor(doctorDTO);
        doctorService.updateByPesel(doctor);
        return ResponseEntity.ok(new CrudPESELResponse(PESEL, "Doctor updated!"));
    }

    @Override
    @RequestMapping(path = "/{doctorId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudResponse> deleteById(@PathVariable Long doctorId) {
        log.info(String.format("Starting deleting Doctor by doctorId: %d", doctorId));
        doctorService.deleteById(doctorId);
        String message = String.format("Doctor with doctorId: %d deleted!", doctorId);
        return ResponseEntity.ok(new CrudResponse(doctorId, message));
    }

    @RequestMapping(path = "/pesel/{pesel}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrudPESELResponse> deleteByPesel(@PathVariable String pesel) {
        log.info(String.format("Starting deleting Doctor by PESEL: %s", pesel));
        doctorService.deleteByPesel(pesel);
        String message = String.format("Doctor with PESEL: %s deleted!", pesel);
        return ResponseEntity.ok(new CrudPESELResponse(pesel, message));
    }

    @RequestMapping(path = "/existsByPesel/{pesel}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean existsByPeselDoctor(@PathVariable String pesel) {
        log.info("Checking if doctor exists by given PESEL: " + pesel);
        return doctorService.existsByPesel(pesel);
    }
}
