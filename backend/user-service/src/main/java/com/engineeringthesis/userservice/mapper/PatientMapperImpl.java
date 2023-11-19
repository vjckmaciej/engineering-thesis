package com.engineeringthesis.userservice.mapper;

import com.engineeringthesis.commons.dto.user.PatientDTO;
import com.engineeringthesis.userservice.entity.Doctor;
import com.engineeringthesis.userservice.entity.Patient;
import com.engineeringthesis.userservice.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PatientMapperImpl implements PatientMapper {
    private final DoctorRepository doctorRepository;

    @Override
    public Patient patientDTOToPatient(PatientDTO patientDTO) {
        if (patientDTO == null) {
            return null;
        }

        Patient patient = new Patient();

        patient.setPatientId(patientDTO.getPatientId());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setPregnancyStartDate(patientDTO.getPregnancyStartDate());
        patient.setPesel(patientDTO.getPesel());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());

        if (patientDTO.getDoctorId() != null) {
            Optional<Doctor> optionalDoctor = doctorRepository.findById(patientDTO.getDoctorId());
            if (optionalDoctor.isPresent()) {
                Doctor doctor = optionalDoctor.get();
                patient.setPregnancyDoctor(doctor);
            } else {
                String message = String.format("Doctor with given doctorId: %d doesn't exist in database!", patientDTO.getDoctorId());
                log.error(message);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
            }
        }


        return patient;
    }

    @Override
    public PatientDTO patientToPatientDTO(Patient patient) {
        if (patient == null) {
            return null;
        }

        PatientDTO patientDTO = new PatientDTO();

        patientDTO.setPatientId(patient.getPatientId());
        patientDTO.setFirstName(patient.getFirstName());
        patientDTO.setLastName(patient.getLastName());
        patientDTO.setBirthDate(patient.getBirthDate());
        patientDTO.setPregnancyStartDate(patient.getPregnancyStartDate());
        patientDTO.setPesel(patient.getPesel());
        patientDTO.setPhoneNumber(patient.getPhoneNumber());

        if (patient.getPregnancyDoctor() != null) {
            patientDTO.setDoctorId(patient.getPregnancyDoctor().getDoctorId());
        }

        return patientDTO;
    }
}
