package com.engineeringthesis.userservice.mapper;

import com.engineeringthesis.userservice.dto.PatientDTO;
import com.engineeringthesis.userservice.entity.Doctor;
import com.engineeringthesis.userservice.entity.Patient;
import com.engineeringthesis.userservice.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PatientMapperImpl implements PatientMapper {
    private final DoctorRepository doctorRepository;

    @Override
    public Patient patientDTOToPatient(PatientDTO patientDTO) {
        if (patientDTO == null) {
            return null;
        }

        Patient patient = new Patient();

        patient.setPatientId(patientDTO.getPatientId());
        patient.setWeekOfPregnancy(patientDTO.getWeekOfPregnancy());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setPhoneNumber(patientDTO.getPhoneNumber());

        if (patientDTO.getDoctorId() != null) {
            Optional<Doctor> optionalDoctor = doctorRepository.findById(patientDTO.getDoctorId());
            if (optionalDoctor.isPresent()) {
                Doctor doctor = optionalDoctor.get();
                patient.setPrenatalDoctor(doctor);
            }
//            else {
//
//                // Obsługa przypadku, gdy nie znaleziono lekarza
//                // Można rzucić wyjątkiem lub wykonać inne odpowiednie działania
//            }
        }


        return patient;
    }

    @Override
    public PatientDTO patientToPatientDTO(Patient patient) {
        return null;
    }
}
